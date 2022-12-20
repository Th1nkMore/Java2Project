package com.example.springproject.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The type Release.
 */
@Entity
@Table
@Getter
@Setter
@ToString
public class release {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    public int id;
    /**
     * The Published at.
     */
    @NonNull
    public Timestamp publishedAt;
    /**
     * The Tag name.
     */
    public String tagName;
    /**
     * The Repo.
     */
    @NonNull
    public String repo;
}
