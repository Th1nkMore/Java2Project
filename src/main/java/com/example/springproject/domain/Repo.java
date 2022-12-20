package com.example.springproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type Repo.
 */
@Entity
@Table
@Getter @Setter
public class Repo {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    int id;
    /**
     * The Name.
     */
    String name;
    /**
     * The Developer num.
     */
    int developerNum;
    /**
     * The Most active developer.
     */
    String mostActiveDeveloper;
    /**
     * The Open issues.
     */
    int openIssues;
    /**
     * The Close issues.
     */
    int closeIssues;
    /**
     * The Releases.
     */
    int releases;
    /**
     * The Commit times.
     */
    int commitTimes;

    /**
     * Instantiates a new Repo.
     *
     * @param name                  the name
     * @param developerNum          the developer num
     * @param most_active_developer the most active developer
     * @param open_issues           the open issues
     * @param close_issues          the close issues
     * @param releases              the releases
     * @param commit_times          the commit times
     */
    public Repo(String name, int developerNum, String most_active_developer, int open_issues, int close_issues, int releases, int commit_times) {
        this.name = name;
        this.developerNum = developerNum;
        this.mostActiveDeveloper = most_active_developer;
        this.openIssues = open_issues;
        this.closeIssues = close_issues;
        this.releases = releases;
        this.commitTimes = commit_times;
    }

    /**
     * Instantiates a new Repo.
     */
    public Repo() {

    }
}
