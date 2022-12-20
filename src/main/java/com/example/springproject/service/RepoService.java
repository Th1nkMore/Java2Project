package com.example.springproject.service;

import com.example.springproject.domain.Repo;


import java.util.Map;
import java.util.Optional;

/**
 * The interface Repo service.
 */
public interface RepoService {
    /**
     * Find by repo name optional.
     *
     * @param repo the repo
     * @return the optional
     */
    Optional<Repo> FindByRepoName(String repo);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Repo> FindById(int id);

    /**
     * Solve avg sol time map.
     *
     * @param Repo the repo
     * @return the map
     */
    Map<String, String>  SolveAvgSolTime(String Repo);

    /**
     * Solve most active string.
     *
     * @param Repo the repo
     * @return the string
     */
    String SolveMostActive(String Repo);

    /**
     * Solve active time map.
     *
     * @param Repo the repo
     * @return the map
     */
    Map<String, String> SolveActiveTime(String Repo);

    /**
     * Commits between release map.
     *
     * @param Repo the repo
     * @return the map
     */
    Map<String, String> CommitsBetweenRelease(String Repo);

    /**
     * Top 10 developer map.
     *
     * @param Repo the repo
     * @return the map
     */
    Map<String, Integer> Top10Developer(String Repo);
}
