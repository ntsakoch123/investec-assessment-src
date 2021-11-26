package za.co.investec.assessment.clientmanagement.application.assembler;

import lombok.extern.slf4j.Slf4j;
import za.co.investec.assessment.clientmanagement.domain.model.Client;
import za.co.investec.assessment.clientmanagement.domain.model.PhysicalAddress;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientAddressModel;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;

@Slf4j
public class ClientModelAssembler {

    public static ClientModel createClientModel(Client client){
        log.info("Creating ClientModel...");
        ClientModel clientModel = new ClientModel();
        clientModel.setClientId(client.getClientId());
        clientModel.setIdNumber(client.getIdNumber().getValue());
        clientModel.setFirstName(client.getFirstName().getValue());
        clientModel.setLastName(client.getLastName().getValue());

        if (client.getMobileNumber().isPresent()) {
            clientModel.setMobileNumber(client.getMobileNumber().get().getValue());
        }

        if (client.getPhysicalAddress().isPresent()) {
            log.info("Adding client address to ClientModel");
            PhysicalAddress physicalAddress = client.getPhysicalAddress().get();
            ClientAddressModel addressModel = new ClientAddressModel();
            addressModel.setHouseNumber(physicalAddress.getHouseNumber());
            addressModel.setPostalCode(physicalAddress.getPostalCode());
            addressModel.setSuburb(physicalAddress.getSuburb());
            addressModel.setStreetName(physicalAddress.getStreetName());
            clientModel.setPhysicalAddress(addressModel);
        }
        log.info("ClientModel created.");
        return clientModel;
    }
}
