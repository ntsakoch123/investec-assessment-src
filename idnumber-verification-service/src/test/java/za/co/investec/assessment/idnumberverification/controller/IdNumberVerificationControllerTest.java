package za.co.investec.assessment.idnumberverification.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import za.co.investec.assessment.idnumberverification.model.IdNumberVerificationResponse;
import za.co.investec.assessment.idnumberverification.service.IdNumberVerificationService;


import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class IdNumberVerificationControllerTest {

    @Mock
    private IdNumberVerificationService idNumberVerificationService;

    @InjectMocks
    IdNumberVerificationController idNumberVerificationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new IdNumberVerificationController(idNumberVerificationService)).build();
    }

    @Test
    public void test_valid_id_number() throws Exception {
        Mockito.when(idNumberVerificationService.validateIdNumber("9011225345085")).thenAnswer(invocationOnMock -> new IdNumberVerificationResponse(true));
        mockMvc.perform(post("/id-number/verify")
                        .param("idNumber", "9011225345085")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("valid", is(true)));

    }

    @Test
    public void test_invalid_id_number() throws Exception {
        Mockito.when(idNumberVerificationService.validateIdNumber("9011225345085")).thenAnswer(invocationOnMock -> new IdNumberVerificationResponse(false));
        mockMvc.perform(post("/id-number/verify")
                        .param("idNumber", "9011225345085")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("valid", is(false)));

    }
}