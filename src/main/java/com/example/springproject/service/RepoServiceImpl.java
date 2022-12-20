package com.example.springproject.service;

import com.example.springproject.dao.*;
import com.example.springproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * The type Repo service.
 */
@Service
public class RepoServiceImpl implements RepoService {
    @Autowired
    private repoDao repoDao;
    @Autowired
    private issueDao issueDao;
    @Autowired
    private commitDao commitDao;
    @Autowired
    private releaseDao releaseDao;
    @Autowired
    private developerDao developerDao;

    @Override
    public Optional<Repo> FindByRepoName(String repo) {
        return repoDao.findByName(repo);
    }

    @Override
    public Optional<Repo> FindById(int id) {
        return repoDao.findById(id);
    }

    @Override
    public Map<String, String>  SolveAvgSolTime(String Repo) {
        long timeSum = 0;
        int cnt = 0;
        long maxValue = Long.MIN_VALUE;
        long minValue = Long.MAX_VALUE;
        for (issue issue : issueDao.findByRepoIgnoreCase(Repo)) {
            if (issue.getState().equals("closed")) {
                cnt++;
                long temp = (issue.getClosedAt().getTime() - issue.getCreatedAt().getTime())/1000;
                if (temp > maxValue) {
                    maxValue = temp;
                }
                if (temp < minValue) {
                    minValue = temp;
                }
                timeSum += temp;
            }
        }
        long avgTime = timeSum / cnt;
        Map<String, String> result = new LinkedHashMap<>();
        // average
        result.put("resolution time",SolveDate(avgTime));
        result.put("max solve time",SolveDate(maxValue));
        result.put("min solve time",SolveDate(minValue));
        result.put("extreme difference",SolveDate(maxValue - minValue));
        return result;
    }

    /**
     * Solve date string.
     *
     * @param time the time
     * @return the string
     */
    public String SolveDate(long time){
        long days = time / 86400;
        long hours = time / 86400 % 24;
        long minutes = time / 60 % 60;
        long seconds = time % 60;
        return days + "d " + hours + "h " + minutes + "min " + seconds + "s";
    }
    @Override
    public String SolveMostActive(String RepoName) {
        Repo repo = repoDao.findByNameIgnoreCase(RepoName);
        return repo.getMostActiveDeveloper();
    }

    @Override
    public Map<String, String> SolveActiveTime(String Repo) {
        // global time
        int morning = 0, afternoon = 0, evening = 0, midnight = 0;
        int cnt = 0;
        for (commit commit : commitDao.findByRepo(Repo)) {
            cnt++;
            long time = commit.getDate().getTime() / 1000;
            int hours = (int) (time / 86400 % 24);
            if (hours >= 6 && hours <= 11)
                morning++;
            else if (hours >= 13 && hours <= 18)
                afternoon++;
            else if (hours >= 18 && hours <= 22)
                evening++;
            else if (hours == 23 || hours <= 3)
                midnight++;
        }

//        int total = morning + afternoon + evening + midnight;
        Map<String, String> timeInfo = new LinkedHashMap<>();
        timeInfo.put("morning",String.format("%.3f", (double) morning / cnt));
        timeInfo.put("afternoon", String.format("%.3f", (double) afternoon / cnt));
        timeInfo.put("evening",String.format("%.3f", (double) evening / cnt));
        timeInfo.put("midnight",String.format("%.3f", (double) midnight / cnt));

        return timeInfo;
    }

    @Override
    public Map<String, String> CommitsBetweenRelease(String Repo) {
        Map<String, String> result = new LinkedHashMap<>();
        int cnt = 0;
        Timestamp temp = new Timestamp(0);
        for (release release: releaseDao.findByRepoOrderByPublishedAtAsc(Repo)){
            cnt++;
            if (cnt!=1){
                result.put("between"+ cnt, String.valueOf(commitDao.findByRepoAndDateBetween(Repo, temp, release.getPublishedAt()).size()));
            }
            temp = release.getPublishedAt();
            result.put(release.getTagName(), release.getPublishedAt().toString().substring(0,10));
        }
        return result;
    }

    @Override
    public Map<String, Integer> Top10Developer(String Repo) {
        Map<String, Integer> result = new LinkedHashMap<>();
        developerDao.findByRepoOrderByContributionsDesc(Repo).stream().limit(10).forEach(e -> result.put(e.getName(), e.getContributions()));
        return result;
    }
}
