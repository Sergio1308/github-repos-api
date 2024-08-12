package com.company.githubReposApi.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepository {
    private String name;
    private Owner owner;
    private boolean fork;
    private List<Branch> branches;

    public GitHubRepository(String name, Owner ownerLogin, List<Branch> branches) {
        this.name = name;
        this.owner = ownerLogin;
        this.branches = branches;
    }
}
