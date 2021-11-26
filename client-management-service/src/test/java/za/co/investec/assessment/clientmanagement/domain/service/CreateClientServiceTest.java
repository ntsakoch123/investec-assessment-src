package za.co.investec.assessment.clientmanagement.domain.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.investec.assessment.clientmanagement.ClientModelHelper;
import za.co.investec.assessment.clientmanagement.application.assembler.ClientDetailsDTOAssembler;
import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.application.exception.ClientIdNumberExistsException;
import za.co.investec.assessment.clientmanagement.application.exception.ClientMobileNumberAlreadyExistsException;
import za.co.investec.assessment.clientmanagement.application.exception.IdNumberVerificationFailedException;
import za.co.investec.assessment.clientmanagement.domain.model.*;
import za.co.investec.assessment.clientmanagement.domain.repository.ClientManagementRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateClientServiceTest {

    @InjectMocks
    private CreateClientService createClientService;

    @Mock
    private ClientManagementRepository clientManagementRepository;

    @Mock
    private IdNumberVerificationService idNumberVerificationService;

    Client sampleClient;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        createClientService = new CreateClientService(clientManagementRepository, idNumberVerificationService);

        FirstName firstName = new FirstName("Ntsako");
        LastName lastName = new LastName("Chabalala");
        IdNumber idNumber = new IdNumber("8605065397083");
        MobileNumber mobileNumber = new MobileNumber("0839657359");

        sampleClient = new Client(firstName, lastName, idNumber, mobileNumber);
    }

    @Test
    public void test_when_create_client_thenReturn_clientId(){
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTOAssembler.createClientDetailsDto(ClientModelHelper.sampleClientModel(), true);

        Mockito.when(clientManagementRepository.save(ArgumentMatchers.any(Client.class))).thenReturn(sampleClient);
        Mockito.when(clientManagementRepository.findByIdNumber(ArgumentMatchers.any(IdNumber.class))).thenReturn(Optional.empty());
        Mockito.doNothing().when(idNumberVerificationService).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
        Long actual = createClientService.createClient(clientDetailsDTO).getValue();
        Mockito.verify(idNumberVerificationService, Mockito.times(1)).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
        Mockito.verify(clientManagementRepository, Mockito.times(1)).save(ArgumentMatchers.any(Client.class));
        assertEquals(0L, actual);
    }

    @Test
    public void test_when_create_client_with_duplicateIdNumber_thenThrow_ClientIdNumberExistsException(){
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTOAssembler.createClientDetailsDto(ClientModelHelper.sampleClientModel(), true);
        Mockito.when(clientManagementRepository.findByIdNumber(ArgumentMatchers.any(IdNumber.class))).thenReturn(Optional.of(sampleClient));
         Assertions.assertThrows(ClientIdNumberExistsException.class, ()->createClientService.createClient(clientDetailsDTO));
        Mockito.verify(clientManagementRepository, Mockito.never()).save(ArgumentMatchers.any(Client.class));
        Mockito.verify(idNumberVerificationService, Mockito.never()).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
    }

    @Test
    public void test_when_create_client_And_idNumber_verificationFailed_thenThrow_IdNumberVerificationException(){
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTOAssembler.createClientDetailsDto(ClientModelHelper.sampleClientModel(), true);
        Mockito.doThrow(IdNumberVerificationFailedException.class).when(idNumberVerificationService).verifyIdNumber(ArgumentMatchers.any());
        Assertions.assertThrows(IdNumberVerificationFailedException.class, ()->createClientService.createClient(clientDetailsDTO));
        Mockito.verify(clientManagementRepository, Mockito.never()).save(ArgumentMatchers.any(Client.class));
        Mockito.verify(idNumberVerificationService, Mockito.times(1)).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
    }

    @Test
    public void test_when_create_client_with_duplicateMobileNumber_thenThrow_ClientMobileNumberAlreadyExistsException(){
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTOAssembler.createClientDetailsDto(ClientModelHelper.sampleClientModel(), true);
        Mockito.when(clientManagementRepository.findByMobileNumber(ArgumentMatchers.any(MobileNumber.class))).thenReturn(Optional.of(sampleClient));
        Assertions.assertThrows(ClientMobileNumberAlreadyExistsException.class, ()->createClientService.createClient(clientDetailsDTO));
        Mockito.verify(clientManagementRepository, Mockito.never()).save(ArgumentMatchers.any(Client.class));
        Mockito.verify(idNumberVerificationService, Mockito.times(1)).verifyIdNumber(ArgumentMatchers.any(IdNumber.class));
    }


}