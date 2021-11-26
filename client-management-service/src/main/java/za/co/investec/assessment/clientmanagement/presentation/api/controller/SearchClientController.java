package za.co.investec.assessment.clientmanagement.presentation.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import za.co.investec.assessment.clientmanagement.application.SearchClientApplication;
import za.co.investec.assessment.clientmanagement.presentation.api.logging.RequestLogger;
import za.co.investec.assessment.clientmanagement.presentation.api.model.ClientModel;
import za.co.investec.assessment.clientmanagement.utils.ApiProperties;

import java.util.List;

@RestController
@CrossOrigin(ApiProperties.HEADERS.ALLOWED_ORIGIN)
public class SearchClientController {

    private final SearchClientApplication searchClientApplication;

    public SearchClientController(SearchClientApplication searchClientApplication) {
        this.searchClientApplication = searchClientApplication;
    }

    @GetMapping(ApiProperties.URLS.CLIENT_RESOURCE_URL)
    public ResponseEntity<ClientModel> findClient(@PathVariable("clientId") Long clientId ) {
        return ResponseEntity.ok(searchClientApplication.findClientById(clientId));
    }

    @RequestLogger
    @GetMapping(ApiProperties.URLS.SEARCH_CLIENT_URL)
    public ResponseEntity<List<ClientModel>> searchClient(@RequestParam(required = false) String firstName,
                 @RequestParam(required = false) String mobileNumber, @RequestParam(required = false) String idNumber) {
        List<ClientModel> searchClientResults = searchClientApplication.searchClient(firstName, mobileNumber, idNumber);
        if(searchClientResults.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(searchClientResults);
    }
}
