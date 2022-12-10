package com.example.springproject.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
@ToString
public class issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    public int id;
    public String title;
    @Lob
    public String description;
    @NonNull
    public Timestamp created_at;
    @NonNull
    public Timestamp closed_at;
    @NonNull
    public String state;
    @NonNull
    public String repo;
}
