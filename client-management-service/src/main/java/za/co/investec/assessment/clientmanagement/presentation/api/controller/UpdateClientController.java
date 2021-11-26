package za.co.investec.assessment.clientmanagement.presentation.api.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import za.co.investec.assessment.clientmanagement.application.UpdateClientApplication;
import za.co.investec.assessment.clientmanagement.presentation.api.logging.RequestLogger;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;
import za.co.investec.assessment.clientmanagement.utils.ApiProperties;

import javax.validation.Valid;

@Validated
@RestController
@CrossOrigin(ApiProperties.HEADERS.ALLOWED_ORIGIN)
public class UpdateClientController {

    private final UpdateClientApplication updateClientApplication;

    public UpdateClientController(UpdateClientApplication updateClientApplication) {
        this.updateClientApplication = updateClientApplication;
    }

    @RequestLogger
    @PutMapping(ApiProperties.URLS.UPDATE_CLIENT_URL)
    public ResponseEntity<Void> updateClient(@RequestBody @Valid ClientModel clientModel, @RequestParam("clientId") Long clientId) {
        updateClientApplication.updateClientDetails(clientId, clientModel);
        return ResponseEntity.ok().build();
    }
}
