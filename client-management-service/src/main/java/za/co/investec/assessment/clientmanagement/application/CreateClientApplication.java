package za.co.investec.assessment.clientmanagement.application;

import za.co.investec.assessment.clientmanagement.application.dto.ClientDetailsDTO;
import za.co.investec.assessment.clientmanagement.application.assembler.ClientDetailsDTOAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import za.co.investec.assessment.clientmanagement.domain.service.CreateClientService;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CreateClientApplication {

    private final CreateClientService createClientService;

    public CreateClientApplication(CreateClientService createClientService) {
        this.createClientService = createClientService;
    }

    public Long createClient(ClientModel clientModel) {
        ClientDetailsDTO clientDetailsDTO = ClientDetailsDTOAssembler.createClientDetailsDto(clientModel, true);
        return createClientService.createClient(clientDetailsDTO).getValue();
    }
}
