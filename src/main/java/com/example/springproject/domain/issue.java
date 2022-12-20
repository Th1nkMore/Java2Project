package com.example.springproject.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The type Issue.
 */
@Entity
@Table
@Getter
@Setter
@ToString
public class issue {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    public int id;
    /**
     * The Title.
     */
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    public String title;
    /**
     * The Description.
     */
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    public String description;
    /**
     * The Created at.
     */
    @NonNull
    public Timestamp createdAt;
    /**
     * The Closed at.
     */
    @NonNull
    public Timestamp closedAt;
    /**
     * The State.
     */
    @NonNull
    public String state;
    /**
     * The Repo.
     */
    @NonNull
    public String repo;
}
