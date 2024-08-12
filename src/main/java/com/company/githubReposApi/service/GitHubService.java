package com.company.githubReposApi.service;

import com.company.githubReposApi.dto.GitHubRepositoryDTO;
import com.company.githubReposApi.exception.UserNotFoundException;
import com.company.githubReposApi.model.Branch;
import com.company.githubReposApi.model.GitHubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GitHubService {

    private final WebClient webClient;

    public GitHubService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<GitHubRepositoryDTO> getUserRepositories(String username) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.error(
                        new UserNotFoundException("User not found: " + username)))
                .bodyToFlux(GitHubRepository.class)
                .filter(repo -> !repo.isFork())
                .flatMap(this::enrichRepositoryWithBranches)
                .map(GitHubRepositoryDTO::new);
    }

    public Mono<GitHubRepository> enrichRepositoryWithBranches(GitHubRepository repository) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}/branches",
                        repository.getOwner().getLogin(),
                        repository.getName())
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList()
                .map(branches -> {
                    repository.setBranches(branches);
                    return repository;
                });
    }
}
