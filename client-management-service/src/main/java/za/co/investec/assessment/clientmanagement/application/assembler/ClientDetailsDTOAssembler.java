package za.co.investec.assessment.clientmanagement.application.assembler;

import lombok.extern.slf4j.Slf4j;
import za.co.investec.assessment.clientmanagement.application.dto.ClientAddressDTO;
import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientAddressModel;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;

import java.util.Objects;

@Slf4j
public class ClientDetailsDTOAssembler {

    public static ClientDetailsDTO createClientDetailsDto(ClientModel clientModel, boolean create) {
        log.info("Creating ClientDetailsDTO...");
        String firstName = Objects.requireNonNull(clientModel.getFirstName());
        String lastName = Objects.requireNonNull(clientModel.getLastName());
        String idNumber = Objects.requireNonNull(clientModel.getIdNumber());
        ClientDetailsDTO clientDetailsDTO = new ClientDetailsDTO(firstName, lastName, idNumber);
        clientDetailsDTO.setMobileNumber(clientModel.getMobileNumber());

        if (clientModel.getPhysicalAddress().isPresent()) {
            log.info("Adding client address to ClientDetailsDTO");
            ClientAddressModel clientAddressModel = clientModel.getPhysicalAddress().get();
            Long houseNumber = Objects.requireNonNull(clientAddressModel.getHouseNumber());
            String streetName = Objects.requireNonNull(clientAddressModel.getStreetName());
            String suburb = Objects.requireNonNull(clientAddressModel.getSuburb());
            Long postalCode = Objects.requireNonNull(clientAddressModel.getPostalCode());
            ClientAddressDTO clientAddressDTO = new ClientAddressDTO(houseNumber, streetName, suburb, postalCode);
            clientAddressDTO.setAddressRemoved(clientAddressModel.isRemoved());
            if (create) clientAddressDTO.setAddressRemoved(false);
            clientDetailsDTO.setClientAddressDTO(clientAddressDTO);
        }
        log.info("ClientDetailsDTO created.");
        return clientDetailsDTO;
    }
}
