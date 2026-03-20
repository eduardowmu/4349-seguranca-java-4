package br.com.forum_hub.domain.autenticacao.google;

import br.com.forum_hub.domain.autenticacao.DadosEmail;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@ConfigurationProperties
public class LoginGoogleService {
    private static final String GOOGLE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private final RestClient restClient;

    @Value("${google.account.client-id}")
    private String clientId;

    @Value("${google.account.client-secret}")
    private String clientSecret;

    @Value("${spring.mail.username}")
    private String googleUsername;

    @Value("${google.account.redirect}")
    private String googleRedirect;

    @Autowired
    public LoginGoogleService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String googleUrl() {
        return GOOGLE_URL+
                "?client_id="+this.clientId+
                "&redirect_uri="+this.googleRedirect+
                "&scope=https://www.googleapis.com/auth/userinfo.email&response_type=code";
    }

    private String obterToken(String code) {
        var response = this.restClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code, "client_id", this.clientId,
                        "client_secret", this.clientSecret, "redirect_uri", this.googleRedirect,
                        "grant_type", "authorization_code"))
                .retrieve()
                .body(Map.class);

        return response.get("id_token").toString();
    }

    public String obterEmail(String code) {
        String token = obterToken(code);
        DecodedJWT decodedJWT = JWT.decode(token);
        System.out.println(decodedJWT.getClaims());
        return decodedJWT.getClaim("email").asString();
    }
}