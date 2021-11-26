package za.co.investec.assessment.clientmanagement.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.investec.assessment.clientmanagement.ClientModelHelper;
import za.co.investec.assessment.clientmanagement.application.assembler.ClientDetailsDTOAssembler;
import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.application.exception.ClientNotFoundException;
import za.co.investec.assessment.clientmanagement.application.exception.IdNumberVerificationFailedException;
import za.co.investec.assessment.clientmanagement.domain.model.*;
import za.co.investec.assessment.clientmanagement.domain.repository.ClientManagementRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateClientServiceTest {

    @InjectMocks
    private UpdateClientService updateClientService;

    @Mock
    private ClientManagementRepository clientManagementRepository;

    @Mock
    private IdNumberVerificationService idNumberVerificationService;

    Client sampleClient;

    FirstName firstName = new FirstName("Ntsako");
    LastName lastName = new LastName("Chabalala");
    IdNumber idNumber = new IdNumber("8605065397083");
    MobileNumber mobileNumber = new MobileNumber("0839657359");

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        updateClientService = new UpdateClientService(clientManagementRepository, idNumberVerificationService);

        sampleClient = new Client(firstName, lastName, idNumber, mobileNumber);
    }

    @Test
    public void test_when_anyField_changed_thenReturn_doUpdateClient(){
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTOAssembler.createClientDetailsDto(ClientModelHelper.sampleClientModel(), false);
        sampleClient = new Client(firstName, lastName, new IdNumber("9011025698750"), mobileNumber);
        Mockito.doAnswer(invocationOnMock -> {
            Object argument = invocationOnMock.getArguments()[0];
            long clientId = Long.parseLong(argument.toString());
            Assertions.assertEquals(1,clientId);
            return Optional.of(sampleClient);
        }).when(clientManagementRepository).findById(ArgumentMatchers.any(Long.class));
        updateClientService.updateClientDetails(new ClientId(1L), clientDetailsDTO);
        Mockito.verify(idNumberVerificationService, Mockito.times(1)).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
        Mockito.verify(clientManagementRepository, Mockito.times(1)).save(ArgumentMatchers.any(Client.class));
    }

    @Test
    public void test_when_clientId_notFound_thenThrow_ClientNotFoundException(){
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTOAssembler.createClientDetailsDto(ClientModelHelper.sampleClientModel(), false);
        Mockito.doThrow(ClientNotFoundException.class).when(clientManagementRepository).findById(ArgumentMatchers.anyLong());
        Assertions.assertThrows(ClientNotFoundException.class, ()->updateClientService.updateClientDetails(new ClientId(1L), clientDetailsDTO));
        Mockito.verify(idNumberVerificationService, Mockito.never()).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
        Mockito.verify(clientManagementRepository, Mockito.never()).save(ArgumentMatchers.any(Client.class));
    }

    @Test
    public void test_when_nothingChanged_then_doNotUpdate(){
         ClientDetailsDTO clientDetailsDTO = new ClientDetailsDTO("Ntsako","Chabalala","8605065397083");
         clientDetailsDTO.setMobileNumber("0839657359");
        Mockito.when(clientManagementRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(sampleClient));
        updateClientService.updateClientDetails(new ClientId(1L), clientDetailsDTO);
        Mockito.verify(idNumberVerificationService, Mockito.never()).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
        Mockito.verify(clientManagementRepository, Mockito.never()).save(ArgumentMatchers.any(Client.class));
    }

    @Test
    public void test_when_addressChanged_then_doUpdate(){
        ClientDetailsDTO clientDetailsDTO = new ClientDetailsDTO("Ntsako","Chabalala","8605065397083");
        clientDetailsDTO.setMobileNumber("0839657359");
        clientDetailsDTO.setClientAddressDTO(ClientDetailsDTOAssembler.createClientDetailsDto(ClientModelHelper.sampleClientModel(), false).getClientAddressDTO().get());
        Mockito.when(clientManagementRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(sampleClient));
        updateClientService.updateClientDetails(new ClientId(1L), clientDetailsDTO);
        Mockito.verify(idNumberVerificationService, Mockito.times(0)).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
        Mockito.verify(clientManagementRepository, Mockito.times(1)).save(ArgumentMatchers.any(Client.class));
    }

}