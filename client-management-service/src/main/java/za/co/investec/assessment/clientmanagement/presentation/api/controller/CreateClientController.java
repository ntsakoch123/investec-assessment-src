package za.co.investec.assessment.clientmanagement.presentation.api.controller;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import za.co.investec.assessment.clientmanagement.application.CreateClientApplication;
import za.co.investec.assessment.clientmanagement.presentation.api.logging.RequestLogger;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;
import za.co.investec.assessment.clientmanagement.utils.ApiProperties;

import javax.validation.Valid;

@Validated
@RestController
//@RequestMapping(headers = )
@CrossOrigin(ApiProperties.HEADERS.ALLOWED_ORIGIN)
public class CreateClientController {

    private final CreateClientApplication createClientApplication;

    public CreateClientController(CreateClientApplication createClientApplication) {
        this.createClientApplication = createClientApplication;
    }

    @RequestLogger
    @PostMapping(ApiProperties.URLS.CREATE_CLIENT_URL)
    public ResponseEntity<Void> createClient(@RequestBody @Valid ClientModel clientModel) {
        Long clientId = createClientApplication.createClient(clientModel);
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.
                methodOn(SearchClientController.class).findClient(clientId));
        return ResponseEntity.created(linkBuilder.toUri()).build();
    }
}
