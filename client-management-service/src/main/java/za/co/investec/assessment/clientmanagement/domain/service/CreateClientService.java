package za.co.investec.assessment.clientmanagement.domain.service;

import lombok.extern.slf4j.Slf4j;
import za.co.investec.assessment.clientmanagement.domain.model.*;
import org.springframework.stereotype.Service;
import za.co.investec.assessment.clientmanagement.application.dto.*;
import za.co.investec.assessment.clientmanagement.domain.repository.ClientManagementRepository;

@Slf4j
@Service
public class CreateClientService extends ClientManagementService {

    private final IdNumberVerificationService idNumberVerificationService;

    public CreateClientService(ClientManagementRepository clientManagementRepository, IdNumberVerificationService idNumberVerificationService) {
        super(clientManagementRepository);
        this.idNumberVerificationService = idNumberVerificationService;
    }

    public ClientId createClient(ClientDetailsDTO clientDetailsDTO) {
        log.info("createClient");
        //Check duplicate id number
        IdNumber idNumber = new IdNumber(clientDetailsDTO.getIdNumber());
        checkIfClientIdNumberAlreadyExists(idNumber);

        //verify using external service
        idNumberVerificationService.verifyIdNumber(idNumber);

        MobileNumber mobileNumber = null;
        //Check if mobileNumber is supplied then we are checking if it already exists
        if (clientDetailsDTO.getMobileNumber().isPresent()) {
            mobileNumber = new MobileNumber(clientDetailsDTO.getMobileNumber().get());
            checkIfClientMobileNumberAlreadyExists(mobileNumber);
        }

        FirstName firstName  = new FirstName(clientDetailsDTO.getFirstName());
        LastName lastName = new LastName(clientDetailsDTO.getLastName());
        Client newClient = new Client(firstName, lastName, idNumber, mobileNumber);

        //Populate physicalAddress if changed(added/deleted)
        updateClientAddressIfSuppliedOrChanged(newClient, clientDetailsDTO);

        ClientId newClientId = new ClientId(clientManagementRepository.save(newClient).getClientId());
        log.info("Client created: {}", newClientId);
        return newClientId;
    }


}
