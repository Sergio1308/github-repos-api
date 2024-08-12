package com.company.githubReposApi.controller;

import com.company.githubReposApi.dto.GitHubRepositoryDTO;
import com.company.githubReposApi.service.GitHubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/users/{username}/repos")
    public Flux<GitHubRepositoryDTO> getUserRepositories(@PathVariable String username) {
        return gitHubService.getUserRepositories(username);
    }
}
