package com.example.springproject.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Repo {
    @Id
    int id;
    String name;
    int developerNum;
    int most_active_developer;
    int open_issues;
    int close_issues;
    double issue_solve_average;
    int issue_solve_max_day;
    int issue_solve_min_day;
    int releases;
    int commit_times;
    int releases_top10_commits;
    int releases_commits;

    public Repo(String name, int developerNum, int most_active_developer, int open_issues, int close_issues, double issue_solve_average, int issue_solve_max_day, int issue_solve_min_day, int releases, int commit_times, int releases_top10_commits, int releases_commits) {
        this.name = name;
        this.developerNum = developerNum;
        this.most_active_developer = most_active_developer;
        this.open_issues = open_issues;
        this.close_issues = close_issues;
        this.issue_solve_average = issue_solve_average;
        this.issue_solve_max_day = issue_solve_max_day;
        this.issue_solve_min_day = issue_solve_min_day;
        this.releases = releases;
        this.commit_times = commit_times;
        this.releases_top10_commits = releases_top10_commits;
        this.releases_commits = releases_commits;
    }

    public Repo(){
        this.name = "name";
        this.developerNum = 0;
        this.most_active_developer = 0;
        this.open_issues = 0;
        this.close_issues = 0;
        this.issue_solve_average = 0;
        this.issue_solve_max_day = 0;
        this.issue_solve_min_day = 0;
        this.releases = 0;
        this.commit_times = 0;
        this.releases_top10_commits = 0;
        this.releases_commits = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeveloperNum() {
        return developerNum;
    }
}
