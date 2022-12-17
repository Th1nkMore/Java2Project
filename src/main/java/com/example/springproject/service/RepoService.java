package com.example.springproject.service;

import com.example.springproject.domain.Repo;

import java.util.Optional;

public interface RepoService {

    Repo FindByRepoName(String repo);
    Optional<Repo> FindById(int id);
    void SolveAvgSolTime();
    void SolveMostActive();

}
