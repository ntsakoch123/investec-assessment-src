package za.co.investec.assessment.clientmanagement.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.investec.assessment.clientmanagement.application.dto.SearchClientDTO;
import za.co.investec.assessment.clientmanagement.application.exception.ClientNotFoundException;
import za.co.investec.assessment.clientmanagement.domain.model.*;
import za.co.investec.assessment.clientmanagement.domain.repository.ClientManagementRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SearchClientService extends ClientManagementService {

    public SearchClientService(ClientManagementRepository clientManagementRepository) {
        super(clientManagementRepository);
    }

    public List<Client> searchByIdNumberOrFirstNameOrMobileNumber(SearchClientDTO searchClientDTO) {
        log.info("searchByIdNumberOrFirstNameOrMobileNumber: {}", searchClientDTO);
        if (searchClientDTO.getFirstName().isPresent() || searchClientDTO.getMobileNumber().isPresent()
                || searchClientDTO.getIdNumber().isPresent()) {
            IdNumber idNumber = new IdNumber(searchClientDTO.getIdNumber().orElse(""));
            FirstName firstName = new FirstName(searchClientDTO.getFirstName().orElse(""));
            MobileNumber lastName = new MobileNumber(searchClientDTO.getMobileNumber().orElse(""));
            return clientManagementRepository.findByIdNumberOrFirstNameOrMobileNumber(idNumber, firstName, lastName);
        }
        log.info("searchByIdNumberOrFirstNameOrMobileNumber finished and no results found.");
        return Collections.emptyList();
    }

    public Client findClientById(ClientId clientId){
        return clientManagementRepository.findById(clientId.getValue()).
                orElseThrow(() -> new ClientNotFoundException(clientId));
    }
}
