package com.company.githubReposApi.dto;

import com.company.githubReposApi.model.GitHubRepository;
import java.util.List;
import java.util.stream.Collectors;

public record GitHubRepositoryDTO(String name, String ownerLogin, List<BranchDTO> branches) {
    public GitHubRepositoryDTO(GitHubRepository gitHubRepository) {
        this(gitHubRepository.getName(), gitHubRepository.getOwner().getLogin(),
                gitHubRepository.getBranches().stream().map(BranchDTO::new)
                        .collect(Collectors.toList()));
    }
}