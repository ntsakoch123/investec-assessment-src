package za.co.investec.assessment.idnumberverification.integration;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import za.co.investec.assessment.idnumberverification.model.IdNumberVerificationResponse;

@Slf4j
@Component
public class IdNumberVerificationClient {

    private final String apiUrl;
    private final String apiKey;
    private final String apiHost;

    private final OkHttpClient okHttpClient;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String CONTENT_TYPE = "application/json";

    public IdNumberVerificationClient(@Value("${idnumber-validation-api-url}") String apiUrl,
                                      @Value("${x-rapidapi-key}") String apiKey,
                                      @Value("${x-rapidapi-host}") String apiHost, OkHttpClient okHttpClient) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.apiHost = apiHost;
        this.okHttpClient = okHttpClient;
    }

    public IdNumberVerificationResponse validate(String idNumber) {
        log.info("validate:{}", idNumber);
        String json = "{\"idno\":\""+idNumber+"\"}";
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("content-type", CONTENT_TYPE)
                .addHeader("x-rapidapi-host", apiHost)
                .addHeader("x-rapidapi-key", apiKey)
                .build();
        try {
            log.info("calling validation api url: {}", request.url());
            log.info("Request headers: {}", request.headers());
            log.info("Request body: {}", json);
            Response response = okHttpClient.newCall(request).execute();
            String responseBody = response.body().string();
            log.info("validation complete with response: {}", response.code());
            log.info("response body: {}", responseBody);
            return new Gson().fromJson(responseBody, IdNumberVerificationResponse.class);
        } catch (Exception e) {
            log.error("Exception occurred on validate: ", e);
            throw  new RuntimeException(e);
        }
    }
}
