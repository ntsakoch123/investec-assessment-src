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
import org.springframework.web.bind.MissingServletRequestParameterException;
import za.co.investec.assessment.clientmanagement.ClientModelHelper;
import za.co.investec.assessment.clientmanagement.application.UpdateClientApplication;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;
import za.co.investec.assessment.clientmanagement.utils.ApiProperties;
import za.co.investec.assessment.clientmanagement.utils.JsonUtils;

import java.io.PrintWriter;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UpdateClientControllerTest {

    @Mock
    private UpdateClientApplication updateClientApplication;

    @InjectMocks
    private UpdateClientController updateClientController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UpdateClientController(updateClientApplication)).build();
    }

    @Test
    public void test_when_update_client_request_thenReturn_Ok() throws Exception {
        ClientModel requestBody = ClientModelHelper.sampleClientModel();
        mockMvc.perform(put(ApiProperties.URLS.UPDATE_CLIENT_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("clientId","1")
                        .content(JsonUtils.toString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_when_update_client_request_with_missing_firstName_thenReturn_badRequest() throws Exception {
        ClientModel requestBody = ClientModelHelper.sampleClientModel();
        requestBody.setFirstName(null);
        mockMvc.perform(put(ApiProperties.URLS.UPDATE_CLIENT_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("clientId", "1")
                        .content(JsonUtils.toString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(result -> {
                    Exception handled = result.getResolvedException();
                    assertNotNull(handled,"exception must not be null");
                    assertTrue(MethodArgumentNotValidException.class.isAssignableFrom(handled.getClass()),"MethodArgumentNotValidException expected");
                });
    }

    @Test
    public void test_when_update_client_request_with_missing_clientId_thenReturn_badRequest() throws Exception {
        ClientModel requestBody = ClientModelHelper.sampleClientModel();
        mockMvc.perform(put(ApiProperties.URLS.UPDATE_CLIENT_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(result -> {
                    PrintWriter wr = result.getResponse().getWriter();
                    wr.println();
                    Exception handled = result.getResolvedException();
                    assertNotNull(handled,"exception must not be null");
                    assertTrue(MissingServletRequestParameterException.class.isAssignableFrom(handled.getClass()),"MissingServletRequestParameterException expected");
                });
    }

}