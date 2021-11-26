package za.co.investec.assessment.clientmanagement;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
//import za.co.investec.assessment.clientmanagement.config.TestConfig;
import za.co.investec.assessment.clientmanagement.config.TestConfig;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorType;
import za.co.investec.assessment.clientmanagement.utils.ApiProperties;

import java.util.LinkedHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Integration tests from presentation layer
 * Only test cases for positive scenarios other scenarios covered by unit tests
 */

@Import(TestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(classes = ClientManagementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientManagementApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String apiUri;

    @BeforeEach
    public void init() {
        apiUri = "http://localhost:" + port;
    }

    @Test
    @Order(1)
    public void test_when_create_client_request_thenReturn_resourceLocation() {
        ResponseEntity<Void> createClientResponse = testRestTemplate.postForEntity(apiUri + ApiProperties.URLS.CREATE_CLIENT_URL, ClientModelHelper.sampleClientModel(), Void.class);
        assertNotNull(createClientResponse, "createClientResponse must not be null");
        assertEquals(apiUri + "/clients/1/v1", createClientResponse.getHeaders().get("location").get(0));
    }

    @Test
    @Order(2)
    public void test_when_update_client_request_thenReturn_OK() {
         assertDoesNotThrow(() -> testRestTemplate.put(apiUri + ApiProperties.URLS.CREATE_CLIENT_URL+"?clientId=1", ClientModelHelper.sampleUpdatedClientModel()));
    }

    @Test
    @Order(3)
    public void test_when_search_client_request_thenReturn_updated_client() {
        String searchUrl = apiUri + ApiProperties.URLS.SEARCH_CLIENT_URL + "?firstName=Ntsako";
        ResponseEntity<ClientModel[]> searchClientResponse = testRestTemplate.getForEntity(searchUrl, ClientModel[].class);
        System.out.println(searchClientResponse);
        assertNotNull(searchClientResponse, "searchClientResponse must not be null");
        assertTrue(searchClientResponse.hasBody(), "search client respoonse must have body");
        ClientModel[] clients = searchClientResponse.getBody();
        assertEquals(1, clients.length, "search client should return only one client");
        ClientModel expected = ClientModelHelper.sampleUpdatedClientModel();
        expected.setClientId(1L);
        System.out.println("updated: "+clients[0]);
        assertThat("clients details properties must be equal", clients[0], samePropertyValuesAs(expected, "physicalAddress"));
        assertThat("clients address properties must be equal", clients[0].getPhysicalAddress(), samePropertyValuesAs(expected.getPhysicalAddress()));
    }

    /*
    * Although I'm only testing positive scenarios, the test case below is added to ensure that proper error response model is returned in case of any errors
     */
    @Test
    @Order(4)
    public void test_when_create_client_request_with_missing_firstName_thenReturn_errorResponse_ValidationError() throws Exception {
        ClientModel model = ClientModelHelper.sampleClientModel();
        model.setFirstName(null);
        ResponseEntity<?> createClientResponse = testRestTemplate.postForEntity(apiUri + ApiProperties.URLS.CREATE_CLIENT_URL, model, Object.class);
        LinkedHashMap<String, Object> hm = (LinkedHashMap<String, Object>) createClientResponse.getBody();
        LinkedHashMap<String, Object> error = (LinkedHashMap<String, Object>) hm.get("error");
        assertEquals(error.get("type"), ErrorType.VALIDATION_FAILURES.toString());
    }

}