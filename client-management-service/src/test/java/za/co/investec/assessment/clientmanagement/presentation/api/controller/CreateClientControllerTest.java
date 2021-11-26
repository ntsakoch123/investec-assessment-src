package za.co.investec.assessment.clientmanagement.presentation.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import za.co.investec.assessment.clientmanagement.ClientModelHelper;
import za.co.investec.assessment.clientmanagement.application.CreateClientApplication;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;
import za.co.investec.assessment.clientmanagement.utils.ApiProperties;
import za.co.investec.assessment.clientmanagement.utils.JsonUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CreateClientControllerTest {

    @Mock
    private CreateClientApplication createClientApplication;

    @InjectMocks
    private CreateClientController createClientController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CreateClientController(createClientApplication)).build();
    }

    @Test
    public void test_when_create_client_request_thenReturn_clientUrlLocation() throws Exception {
        ClientModel requestBody = ClientModelHelper.sampleClientModel();
        Mockito.when(createClientApplication.createClient(ArgumentMatchers.any(ClientModel.class))).then(invocationOnMock -> 1L);
        mockMvc.perform(post(ApiProperties.URLS.CREATE_CLIENT_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","http://localhost/clients/1/v1"));
    }

    @Test
    public void test_when_create_client_request_with_invalid_mobileNumber_thenReturn_badRequest() throws Exception {
        ClientModel requestBody = ClientModelHelper.sampleClientModel();
        requestBody.setMobileNumber("098");
        mockMvc.perform(post(ApiProperties.URLS.CREATE_CLIENT_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(result -> {
                    Exception handled = result.getResolvedException();
                    assertNotNull(handled,"exception must not be null");
                    assertTrue(MethodArgumentNotValidException.class.isAssignableFrom(handled.getClass()),"MethodArgumentNotValidException expected");
                });
    }
}