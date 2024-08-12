package com.company.githubReposApi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {
    @Bean
    public WebClient webClient(@Value("${github.token}") String token) {
        WebClient.Builder builder = WebClient.builder()
                .baseUrl("https://api.github.com/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "GitHubReposApi");

        if (token != null && !token.isBlank()) {
            log.info("GitHub access token was provided, successfully added to the HTTP header");
            builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        } else {
            log.info("GitHub access token was not provided, unauthenticated requests "
                    + "may exceed the rate limit");
        }

        return builder.build();
    }
}
