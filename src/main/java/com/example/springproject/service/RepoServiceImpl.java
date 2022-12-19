package com.example.springproject.service;

import com.example.springproject.dao.commitDao;
import com.example.springproject.dao.issueDao;
import com.example.springproject.dao.repoDao;
import com.example.springproject.domain.Repo;
import com.example.springproject.domain.commit;
import com.example.springproject.domain.issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RepoServiceImpl implements RepoService {
    @Autowired
    private repoDao repoDao;
    @Autowired
    private issueDao issueDao;
    @Autowired
    private commitDao commitDao;

    @Override
    public Optional<Repo> FindByRepoName(String repo) {
//        Repo Repo = new Repo();
//        Repo.setName(repo);
//        Repo.setCommit_times(1123);
//        Repo.setDeveloperNum(2151);
//        Repo.setReleases(9);
//        Repo.setOpen_issues(100);
//        return Repo;

        return repoDao.findByName(repo);
    }

    @Override
    public Optional<Repo> FindById(int id) {
        return repoDao.findById(id);
    }

    @Override
    public String SolveAvgSolTime() {
        long timeSum = 0;
        int cnt = 0;
        for (issue issue : issueDao.findAll()) {
            if (issue.getState().equals("closed")) {
                cnt++;
                timeSum += (issue.getClosed_at().getTime() - issue.getCreated_at().getTime()) / 1000;
            }
        }
        long avgTime = timeSum / cnt;
        long days = avgTime / 86400;
        long hours = avgTime / 86400 % 24;
        long minutes = avgTime / 60 % 60;
        long seconds = avgTime % 60;

        return days + "d " + hours + "h " + minutes + "min " + seconds + "s";
    }

    @Override
    public ArrayList<String> SolveMostActive() {
        ArrayList<String> mostActiveList = new ArrayList<>();
        for (Repo repo : repoDao.findAll()) {
            mostActiveList.add(repo.getName() + ": " + repo.getMost_active_developer());
        }
        return mostActiveList;
    }

    @Override
    public ArrayList<String> SolveActiveTime() {
        // global time
        int morning = 0, afternoon = 0, evening = 0, midnight = 0;
        for (commit commit : commitDao.findAll()) {
            long time = commit.getDate().getTime() / 1000;
            int hours = (int) (time / 86400 % 24);
            if (hours >= 6 && hours <= 11)
                morning++;
            else if (hours >= 12 && hours <= 17)
                afternoon++;
            else if (hours >= 18)
                evening++;
            else
                midnight++;
        }

        int total = morning + afternoon + evening + midnight;
        ArrayList<String> timeInfo = new ArrayList<>();
        timeInfo.add(String.format("morning: %.3f", (double) morning / total));
        timeInfo.add(String.format("afternoon: %.3f", (double) afternoon / total));
        timeInfo.add(String.format("evening: %.3f", (double) evening / total));
        timeInfo.add(String.format("midnight: %.3f", (double) midnight / total));

        return timeInfo;
    }
}
