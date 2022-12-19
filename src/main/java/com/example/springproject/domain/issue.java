package com.example.springproject.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

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
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    public String title;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
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
