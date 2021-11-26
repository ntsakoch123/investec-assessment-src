package za.co.investec.assessment.clientmanagement.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.investec.assessment.clientmanagement.ClientModelHelper;
import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.domain.model.ClientId;
import za.co.investec.assessment.clientmanagement.domain.service.CreateClientService;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;


@ExtendWith({MockitoExtension.class})
class CreateClientApplicationTest {

    @Mock
    private CreateClientService createClientService;

    @InjectMocks
    private CreateClientApplication createClientApplication;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
         createClientApplication = new CreateClientApplication(createClientService);
    }

    @Test
    public void test_when_createClient_thenReturn_clientId(){
        ClientId clientId = new ClientId(1L);
        ClientModel sampleModel = ClientModelHelper.sampleClientModel();
        Mockito.when(createClientService.createClient(ArgumentMatchers.any(ClientDetailsDTO.class))).thenAnswer(invocationOnMock -> clientId);
        Long actualClientId = createClientApplication.createClient(sampleModel);
        Assertions.assertEquals(1, actualClientId);
    }


}