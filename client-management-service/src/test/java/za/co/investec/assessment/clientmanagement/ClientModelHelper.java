package za.co.investec.assessment.clientmanagement;

import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientAddressModel;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;

public class ClientModelHelper {

    public static ClientModel sampleClientModel() {
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setFirstName("Ntsako");
        model.setLastName("Chabalala");
        model.setIdNumber("8605065397083");
        model.setMobileNumber("0839657359");
        ClientAddressModel addressModel = new ClientAddressModel();
        addressModel.setStreetName("JJJ");
        addressModel.setHouseNumber(1234L);
        addressModel.setSuburb("PPP");
        addressModel.setPostalCode(544L);
        model.setPhysicalAddress(addressModel);
        return model;
    }

    public static ClientModel sampleUpdatedClientModel() {
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setFirstName("Ntsako");
        model.setLastName("Chabalala");
        model.setIdNumber("8605065397083");
//        model.setMobileNumber("0839657359");
        ClientAddressModel addressModel = new ClientAddressModel();
        addressModel.setStreetName("JJJ Updated");
        addressModel.setHouseNumber(1234L);
        addressModel.setSuburb("PPP");
        addressModel.setPostalCode(544L);
        model.setPhysicalAddress(addressModel);
        return model;
    }
}
