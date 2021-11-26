package za.co.investec.assessment.clientmanagement.domain.service;

import lombok.extern.slf4j.Slf4j;
import za.co.investec.assessment.clientmanagement.application.dto.ClientAddressDTO;
import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.domain.model.IdNumber;
import za.co.investec.assessment.clientmanagement.application.exception.ClientIdNumberExistsException;
import za.co.investec.assessment.clientmanagement.application.exception.ClientMobileNumberAlreadyExistsException;
import za.co.investec.assessment.clientmanagement.domain.model.Client;
import za.co.investec.assessment.clientmanagement.domain.model.MobileNumber;
import za.co.investec.assessment.clientmanagement.domain.model.PhysicalAddress;
import za.co.investec.assessment.clientmanagement.domain.repository.ClientManagementRepository;

import java.util.Optional;

@Slf4j
public abstract class ClientManagementService {

    protected final ClientManagementRepository clientManagementRepository;

    protected ClientManagementService(ClientManagementRepository clientManagementRepository) {
        this.clientManagementRepository = clientManagementRepository;
    }

    protected final void checkIfClientIdNumberAlreadyExists(IdNumber idNumber) {
        log.info("Check if idNumber does not belong to an existing client.");
        Optional<Client> client = clientManagementRepository.findByIdNumber(idNumber);
        if (client.isPresent()) throw new ClientIdNumberExistsException(idNumber);
    }

    protected final void checkIfClientMobileNumberAlreadyExists(MobileNumber mobileNumber) {
        log.info("Check if mobileNumber does not belong to an existing client.");
        Optional<Client> client = clientManagementRepository.findByMobileNumber(mobileNumber);
        if (client.isPresent()) throw new ClientMobileNumberAlreadyExistsException(mobileNumber);
    }

    protected boolean updateClientAddressIfSuppliedOrChanged(Client client, ClientDetailsDTO clientDetailsDTO) {
        log.info("updateClientAddressIfSuppliedOrChanged");
        boolean updated = false;
        Optional<ClientAddressDTO> optionalAddress = clientDetailsDTO.getClientAddressDTO();
        if (optionalAddress.isPresent()) {
            ClientAddressDTO clientAddressDTO = optionalAddress.get();
            if (clientAddressDTO.isAddressRemoved() && client.removePhysicalAddress()) {
                log.info("Client address removed.");
                updated = true;
            } else {
                PhysicalAddress clientAddress = new PhysicalAddress(clientAddressDTO.getHouseNumber(),
                        clientAddressDTO.getStreetName(), clientAddressDTO.getSuburb(), clientAddressDTO.getPostalCode());
                if (client.updatePhysicalAddress(clientAddress)) {
                    log.info("Client address updated.");
                    updated = true;
                }
            }
        } else if (client.getPhysicalAddress().isPresent() && client.removePhysicalAddress()) {
            log.info("Client address removed.");
            updated = true;
        }
        return updated;
    }


}
