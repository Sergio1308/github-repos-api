package com.company.githubReposApi.dto;

import com.company.githubReposApi.model.Branch;

public record BranchDTO(String name, String lastCommitSha) {
    public BranchDTO(Branch branch) {
        this(branch.getName(), branch.getCommit().getSha());
    }
}
