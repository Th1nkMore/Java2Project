package com.example.springproject.service;

import com.example.springproject.domain.Repo;
import org.springframework.stereotype.Service;

@Service
public class RepoServiceImpl implements RepoService {

    @Override
    public Repo FindByRepoName(String repo) {
        Repo Repo = new Repo();
        Repo.setName(repo);
        return Repo;
    }
}
