package za.co.investec.assessment.clientmanagement.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.application.assembler.ClientDetailsDTOAssembler;
import za.co.investec.assessment.clientmanagement.domain.model.ClientId;
import za.co.investec.assessment.clientmanagement.domain.service.UpdateClientService;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UpdateClientApplication {

    private final UpdateClientService updateClientService;

    public UpdateClientApplication(UpdateClientService updateClientService) {
        this.updateClientService = updateClientService;
    }

    public void updateClientDetails(Long clientId, ClientModel clientModel){
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTOAssembler.createClientDetailsDto(clientModel, false);
        updateClientService.updateClientDetails(new ClientId(clientId), clientDetailsDTO);
    }
}
