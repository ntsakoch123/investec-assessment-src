package za.co.investec.assessment.clientmanagement.application;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.investec.assessment.clientmanagement.ClientModelHelper;
import za.co.investec.assessment.clientmanagement.application.dto.SearchClientDTO;
import za.co.investec.assessment.clientmanagement.application.exception.NoSearchParameterException;
import za.co.investec.assessment.clientmanagement.domain.model.*;
import za.co.investec.assessment.clientmanagement.domain.service.SearchClientService;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;

@ExtendWith(MockitoExtension.class)
class SearchClientApplicationTest {

    @Mock
    private SearchClientService searchClientService;

    @InjectMocks
    private SearchClientApplication searchClientApplication;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        searchClientApplication = new SearchClientApplication(searchClientService);
    }

    @Test
    public void test_when_searchClient_by_anyField_thenReturn_clientModel() {
        List<Client> sampleClients = Collections.singletonList(sampleClient());
        Mockito.when(searchClientService.searchByIdNumberOrFirstNameOrMobileNumber(ArgumentMatchers.any(SearchClientDTO.class))).thenAnswer(invocationOnMock -> sampleClients);
        List<ClientModel> clients = searchClientApplication.searchClient("Ntsako", "", "");
        ClientModel expected = ClientModelHelper.sampleClientModel();
        MatcherAssert.assertThat(clients.get(0), samePropertyValuesAs(expected, "physicalAddress", "clientId"));
    }

    @Test
    public void test_when_findClient_by_id_thenReturn_clientModel() {
        Client sampleClient = sampleClient();
        Mockito.when(searchClientService.findClientById(ArgumentMatchers.any(ClientId.class))).thenAnswer(invocationOnMock -> sampleClient);
        ClientModel client = searchClientApplication.findClientById(1L);
        ClientModel expected = ClientModelHelper.sampleClientModel();
        MatcherAssert.assertThat(client, samePropertyValuesAs(expected, "physicalAddress", "clientId"));
    }

    @Test
    public void test_when_searchClient_with_no_parameters_thenThrow_NoSearchParameterException() {
         Assertions.assertThrows(NoSearchParameterException.class, () -> searchClientApplication.searchClient("", "", ""));
    }

    private Client sampleClient() {
        FirstName firstName = new FirstName("Ntsako");
        LastName lastName = new LastName("Chabalala");
        IdNumber idNumber = new IdNumber("8605065397083");
        MobileNumber mobileNumber = new MobileNumber("0839657359");
        return new Client(firstName, lastName, idNumber, mobileNumber);
    }
}