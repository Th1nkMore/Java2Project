package com.example.springproject.service;

import com.example.springproject.domain.Repo;

public interface RepoService {

    Repo FindByRepoName(String repo);
}
