package za.co.investec.assessment.clientmanagement.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import za.co.investec.assessment.clientmanagement.ClientModelHelper;
import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.domain.model.ClientId;
import za.co.investec.assessment.clientmanagement.domain.service.UpdateClientService;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;

@ExtendWith(SpringExtension.class)
class UpdateClientApplicationTest {

    @Mock
    private UpdateClientService updateClientService;

    @InjectMocks
    private UpdateClientApplication updateClientApplication;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        updateClientApplication = new UpdateClientApplication(updateClientService);
    }

    @Test
    public void test_when_updateClient_doUpdate(){
        ClientModel sampleModel = ClientModelHelper.sampleClientModel();
        Mockito.doNothing().when(updateClientService).updateClientDetails(ArgumentMatchers.any(ClientId.class), ArgumentMatchers.any(ClientDetailsDTO.class));
       Assertions.assertDoesNotThrow(()->updateClientApplication.updateClientDetails(1L, sampleModel));

    }
}