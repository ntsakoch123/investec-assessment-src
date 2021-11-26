package za.co.investec.assessment.clientmanagement.domain.service;

import lombok.extern.slf4j.Slf4j;
import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.domain.model.*;
import org.springframework.stereotype.Service;
import za.co.investec.assessment.clientmanagement.application.exception.ClientNotFoundException;
import za.co.investec.assessment.clientmanagement.domain.repository.ClientManagementRepository;

@Slf4j
@Service
public class UpdateClientService extends ClientManagementService {

    private final IdNumberVerificationService idNumberVerificationService;

    public UpdateClientService(ClientManagementRepository clientManagementRepository, IdNumberVerificationService idNumberVerificationService) {
        super(clientManagementRepository);
        this.idNumberVerificationService = idNumberVerificationService;
    }

    public void updateClientDetails(ClientId clientId, ClientDetailsDTO clientDetailsDTO) {
        log.info("on updateClientDetails");
        Client client = clientManagementRepository.findById(clientId.getValue()).
                orElseThrow(() -> new ClientNotFoundException(clientId));

        IdNumber idNumber = new IdNumber(clientDetailsDTO.getIdNumber());
        if (!idNumber.sameValueAs(client.getIdNumber().getValue())) {
            checkIfClientIdNumberAlreadyExists(idNumber);
            idNumberVerificationService.verifyIdNumber(idNumber);
        }

        boolean mobileNumberUpdated = false;
        if (clientDetailsDTO.getMobileNumber().isPresent()) {
            MobileNumber mobileNumber = new MobileNumber(clientDetailsDTO.getMobileNumber().get());
            if (client.getMobileNumber().isPresent()) {
                if (!mobileNumber.sameValueAs(client.getMobileNumber().get().getValue())) {
                    checkIfClientMobileNumberAlreadyExists(mobileNumber);
                }
            }
            if (client.updateMobileNumber(mobileNumber)) {
                mobileNumberUpdated = true;
                log.info("mobileNumber updated.");
            }
        } else if (client.getMobileNumber().isPresent() && client.removeMobileNumber()) {
            mobileNumberUpdated = true;
            log.info("mobileNumber removed.");
        }

        FirstName firstName = new FirstName(clientDetailsDTO.getFirstName());
        LastName lastName = new LastName(clientDetailsDTO.getLastName());

        boolean detailsUpdated = client.updateDetails(firstName, lastName, idNumber);
        boolean addressUpdated = updateClientAddressIfSuppliedOrChanged(client, clientDetailsDTO);

        if (addressUpdated || detailsUpdated || mobileNumberUpdated) {
            clientManagementRepository.save(client);
            log.info("Client details updated.");
        }
    }


}
