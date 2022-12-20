package com.example.springproject.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The type Commit.
 */
@Entity
@Table
@Getter
@Setter
@ToString
public class commit {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    public int id;
    /**
     * The Date.
     */
    @NonNull
    public Timestamp date; // getDate()
    /**
     * The Repo.
     */
    @NonNull
    public String repo;
}
