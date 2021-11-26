package za.co.investec.assessment.idnumberverification.config;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OkHttpClient okHttpClient() throws Exception {
        return new OkHttpClient();
    }
}
