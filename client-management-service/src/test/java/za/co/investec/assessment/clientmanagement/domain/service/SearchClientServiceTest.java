package za.co.investec.assessment.clientmanagement.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.investec.assessment.clientmanagement.application.dto.SearchClientDTO;
import za.co.investec.assessment.clientmanagement.application.exception.ClientNotFoundException;
import za.co.investec.assessment.clientmanagement.domain.model.*;
import za.co.investec.assessment.clientmanagement.domain.repository.ClientManagementRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SearchClientServiceTest {

    @InjectMocks
    private SearchClientService searchClientService;

    @Mock
    private ClientManagementRepository clientManagementRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        searchClientService = new SearchClientService(clientManagementRepository);
    }

    @Test
    public  void test_when_search_by_anyParameter_then_returnClient(){
        SearchClientDTO sampleSearchClientDTO = new SearchClientDTO("Ntsako","","");
        Mockito.when(clientManagementRepository.findByIdNumberOrFirstNameOrMobileNumber
                (ArgumentMatchers.any(IdNumber.class), ArgumentMatchers.any(FirstName.class), ArgumentMatchers.any(MobileNumber.class)))
                .thenReturn(Collections.singletonList(sampleClient()));
       List<Client> clients =  searchClientService.searchByIdNumberOrFirstNameOrMobileNumber(sampleSearchClientDTO);
        Assertions.assertEquals(1, clients.size());
        Assertions.assertEquals("Ntsako", clients.get(0).getFirstName().getValue(),"incorrect firstName");
        Assertions.assertEquals("Chabalala", clients.get(0).getLastName().getValue(),"incorrect lastName");
    }

    @Test
    public  void test_when_find_client_by_id_no_parameters_then_returnClient(){
        Mockito.when(clientManagementRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(sampleClient()));
        Client client =  searchClientService.findClientById(new ClientId(1L));
        Assertions.assertEquals("Ntsako", client.getFirstName().getValue(),"incorrect firstName");
        Assertions.assertEquals("Chabalala", client.getLastName().getValue(),"incorrect lastName");
    }

    @Test
    public  void test_when_noClient_found_thenThrow_ClientNotFoundException(){
        Mockito.doThrow(ClientNotFoundException.class).when(clientManagementRepository).findById(ArgumentMatchers.anyLong());
        Assertions.assertThrows(ClientNotFoundException.class, ()->searchClientService.findClientById(new ClientId(1L)));
    }

    private Client sampleClient() {
        FirstName firstName = new FirstName("Ntsako");
        LastName lastName = new LastName("Chabalala");
        IdNumber idNumber = new IdNumber("8605065397083");
        MobileNumber mobileNumber = new MobileNumber("0839657359");
        return new Client(firstName, lastName, idNumber, mobileNumber);
    }

}