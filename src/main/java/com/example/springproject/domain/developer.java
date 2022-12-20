package com.example.springproject.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * The type Developer.
 */
@Entity
@Table
@Getter
@Setter
@ToString
public class developer {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    public int id;
    /**
     * The Name.
     */
    @NonNull
    public String name;
    /**
     * The Contributions.
     */
    public int contributions;
    /**
     * The Repo.
     */
    @NonNull
    public String repo;
}
