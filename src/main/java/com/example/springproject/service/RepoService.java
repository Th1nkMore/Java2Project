package com.example.springproject.service;

import com.example.springproject.domain.Repo;

import java.util.ArrayList;
import java.util.Optional;

public interface RepoService {
    Optional<Repo> FindByRepoName(String repo);

    Optional<Repo> FindById(int id);

    String SolveAvgSolTime();

    ArrayList<String> SolveMostActive();

    ArrayList<String> SolveActiveTime();

}
