package za.co.investec.assessment.clientmanagement.presentation.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import za.co.investec.assessment.clientmanagement.ClientModelHelper;
import za.co.investec.assessment.clientmanagement.application.SearchClientApplication;
import za.co.investec.assessment.clientmanagement.application.exception.ClientNotFoundException;
import za.co.investec.assessment.clientmanagement.domain.model.ClientId;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;
import za.co.investec.assessment.clientmanagement.utils.ApiProperties;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SearchClientControllerTest {

    @Mock
    private SearchClientApplication searchClientApplication;

    @InjectMocks
    private SearchClientController searchClientController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new SearchClientController(searchClientApplication)).build();
    }

    @Test
    public void test_when_find_client_request_theReturn_clientModel() throws Exception {
        ClientModel sampleResponse = ClientModelHelper.sampleClientModel();
        Long clientId = 1L;
        Mockito.when(searchClientApplication.findClientById(clientId)).then(invocationOnMock -> sampleResponse);
        mockMvc.perform(get(ApiProperties.URLS.CLIENT_RESOURCE_URL, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("clientId", is(1)));
    }

    @Test
    public void test_when_find_client_request_with_invalid_clientId_theThrow_ClientNotFoundException() throws Exception {
        Mockito.when(searchClientApplication.findClientById(ArgumentMatchers.any(Long.class))).thenThrow((new ClientNotFoundException(new ClientId(1L))));
        mockMvc.perform(get(ApiProperties.URLS.CLIENT_RESOURCE_URL, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(result -> {
                    Exception handled = result.getResolvedException();
                    assertNotNull(handled, "exception must not be null");
                    assertTrue(ClientNotFoundException.class.isAssignableFrom(handled.getClass()), "ClientNotFoundException expected");
                });
    }

    @Test
    public void test_when_search_client_request_by_any_parameter_thenReturn_clients() throws Exception {
        List<ClientModel> sampleClients = Collections.singletonList(ClientModelHelper.sampleClientModel());
        Mockito.when(searchClientApplication.searchClient("Ntsako", "", "")).then(invocationOnMock -> sampleClients);
        mockMvc.perform(get(ApiProperties.URLS.SEARCH_CLIENT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", "Ntsako")
                .param("mobileNumber", "")
                .param("idNumber", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clientId", is(1)));
    }

}