package za.co.investec.assessment.clientmanagement.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import za.co.investec.assessment.clientmanagement.application.assembler.ClientModelAssembler;
import za.co.investec.assessment.clientmanagement.application.dto.SearchClientDTO;
import za.co.investec.assessment.clientmanagement.domain.model.Client;
import za.co.investec.assessment.clientmanagement.domain.model.ClientId;
import za.co.investec.assessment.clientmanagement.domain.service.SearchClientService;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SearchClientApplication {

    private final SearchClientService searchClientService;

    public SearchClientApplication(SearchClientService searchClientService) {
        this.searchClientService = searchClientService;
    }

    public List<ClientModel> searchClient(String firstName, String mobileNumber, String idNumber) {
        SearchClientDTO searchClientDTO = new SearchClientDTO(firstName, mobileNumber, idNumber);
        List<Client> searchClientsResults = searchClientService.searchByIdNumberOrFirstNameOrMobileNumber(searchClientDTO);
        return searchClientsResults.stream().map(ClientModelAssembler::createClientModel).collect(Collectors.toList());
    }

    public ClientModel findClientById(Long clientId){
        Client client = searchClientService.findClientById(new ClientId(clientId));
        return ClientModelAssembler.createClientModel(client);
    }

}
