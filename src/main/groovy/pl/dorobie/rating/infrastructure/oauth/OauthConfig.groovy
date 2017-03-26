package pl.dorobie.rating.infrastructure.oauth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class OauthConfig {

    @Bean
    RestTemplate oauthRestTemplate() {
        return new RestTemplate()
    }
}
