package za.co.investec.assessment.clientmanagement.utils;

public interface ApiProperties {

    class URLS {

        public static final String API_VERSION = "v1";
        public static final String BASE_URL = "/clients";
        public static final String SEARCH_CLIENT_URL = BASE_URL + "/search/" + API_VERSION;
        public static final String CLIENT_RESOURCE_URL = BASE_URL + "/{clientId}/" + API_VERSION;
        public static final String UPDATE_CLIENT_URL = BASE_URL + "/" + API_VERSION;
        public static final String CREATE_CLIENT_URL = BASE_URL + "/" + API_VERSION;
        public static final String ID_NUMBER_VALIDATION_URL = "/id-number/verify";
    }

    class HEADERS {

        public static final String ALLOWED_ORIGIN = "http://localhost:3000";
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
    }

}
