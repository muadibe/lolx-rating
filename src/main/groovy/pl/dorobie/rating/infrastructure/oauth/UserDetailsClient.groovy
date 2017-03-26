package pl.dorobie.rating.infrastructure.oauth

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.dorobie.rating.domain.UserDetailsProvider
import pl.dorobie.rating.domain.model.UserDetails

@Component
public class UserDetailsClient implements UserDetailsProvider {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsClient.class);

    private final String oauthDetailsUrl
    private final RestTemplate oauthRestTemplate

    @Autowired
    UserDetailsClient(@Value('${oauth.details.url}') String oauthDetailsUrl, RestTemplate oauthRestTemplate) {
        this.oauthDetailsUrl = oauthDetailsUrl
        this.oauthRestTemplate = oauthRestTemplate
    }

    @Override
    UserDetails getUserDetails(String userId, String jwt) {
        try {
            def response = getDetails(userId, jwt)
            log.info("resp user details", response.body)
            return new UserDetails(nick: response.body.nick)
        } catch (Exception e) {
            log.error("cannot resolve user nick: ", e)
            return new UserDetails(nick: userId)
        }
    }

    private ResponseEntity<OAuthUserDetails> getDetails(String userId, String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-type", "application/json");
        headers.set("Authorization", authorizationHeader);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        return oauthRestTemplate.exchange(
                oauthDetailsUrl + "/users/" + userId,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
    }
}
