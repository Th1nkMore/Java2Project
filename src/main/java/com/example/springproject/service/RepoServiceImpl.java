package com.example.springproject.service;

import com.example.springproject.dao.commitDao;
import com.example.springproject.dao.repoDao;
import com.example.springproject.domain.Repo;
import com.example.springproject.domain.commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepoServiceImpl implements RepoService {
    @Autowired
    private repoDao repoDao;
    @Autowired
    private commitDao commitDao;
    @Override
    public Repo FindByRepoName(String repo) {
        Repo Repo = new Repo();
        Repo.setName(repo);
        Repo.setCommit_times(1123);
        Repo.setDeveloperNum(2151);
        Repo.setReleases(9);
        Repo.setOpen_issues(100);
        return Repo;
    }

    @Override
    public Optional<Repo> FindById(int id) {
        return repoDao.findById(id);
    }

    @Override
    public void SolveAvgSolTime() {
        for (commit commit: commitDao.findAll()){
            System.out.println(commit.getDate());
        }
    }

    @Override
    public void SolveMostActive() {

    }
}
