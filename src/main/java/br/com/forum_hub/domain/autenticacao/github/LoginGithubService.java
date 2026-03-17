package br.com.forum_hub.domain.autenticacao.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties
public class LoginGithubService {
    private static final String GITHUB_URL = "https://github.com/login/oauth/authorize";

    @Value("github.account.client-id")
    private String clientId;

    @Value("github.account.client-secret")
    private String clientSecret;

    @Value("github.account.username")
    private String githubUsername;

    @Value("github.account.redirect")
    private String githubRedirect;

    public String githubUrl() {
        return GITHUB_URL+
                "?client_id="+this.clientId+
                "&redirect_uri="+this.githubRedirect+
                "&scope=read:user,user:email";
    }
}