package com.example.springproject.web;

import com.example.springproject.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

/**
 * The type Rest repo controller.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/")
public class RestRepoController {
    @Autowired
    private RepoService repoService;

    /**
     * Solve avg map.
     *
     * @param repo the repo
     * @return the map
     */
    @RequestMapping(value = "SolveAvg", method = RequestMethod.GET)
    public Map<String, String> SolveAvg(String repo) {
        return repoService.SolveAvgSolTime(repo);
    }

    /**
     * Top 10 developer map.
     *
     * @param repo the repo
     * @return the map
     */
    @RequestMapping(value = "Top10Developer", method= RequestMethod.GET)
    public Map<String, Integer> Top10Developer(String repo){
        return repoService.Top10Developer(repo);
    }

    /**
     * Solve active time map.
     *
     * @param repo the repo
     * @return the map
     */
    @RequestMapping(value = "SolveActiveTime", method = RequestMethod.GET)
    public Map<String, String> SolveActiveTime(String repo) {
        return repoService.SolveActiveTime(repo);
    }

    /**
     * Commits between release map.
     *
     * @param repo the repo
     * @return the map
     */
    @RequestMapping(value = "CommitsBetweenRelease", method = RequestMethod.GET)
    public Map<String, String> CommitsBetweenRelease(String repo) {
        return repoService.CommitsBetweenRelease(repo);
    }
}
