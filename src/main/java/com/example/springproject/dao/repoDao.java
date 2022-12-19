package com.example.springproject.dao;
import com.example.springproject.domain.Repo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface repoDao extends CrudRepository<Repo, Integer>{
    Optional<Repo> findByName(String name);
}
