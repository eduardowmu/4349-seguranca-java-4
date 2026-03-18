package br.com.forum_hub.domain.autenticacao.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@ConfigurationProperties
public class LoginGithubService {
    private static final String GITHUB_URL = "https://github.com/login/oauth/authorize";
    private final RestClient restClient;

    @Value("${github.account.client-id}")
    private String clientId;

    @Value("${github.account.client-secret}")
    private String clientSecret;

    @Value("${github.account.username}")
    private String githubUsername;

    @Value("${github.account.redirect}")
    private String githubRedirect;

    @Autowired
    public LoginGithubService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String githubUrl() {
        return GITHUB_URL+
                "?client_id="+this.clientId+
                "&redirect_uri="+this.githubRedirect+
                "&scope=read:user,user:email";
    }

    public String obterToken(String code) {
        var response = this.restClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code, "client_id", this.clientId,
                        "client_secret", this.clientSecret, "redirect_uri", this.githubRedirect))
                .retrieve()
                .body(String.class);

        return response;
    }
}