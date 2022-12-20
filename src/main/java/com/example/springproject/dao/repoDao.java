package com.example.springproject.dao;
import com.example.springproject.domain.Repo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The interface Repo dao.
 */
public interface repoDao extends CrudRepository<Repo, Integer>{
    /**
     * Find by name ignore case repo.
     *
     * @param name the name
     * @return the repo
     */
    Repo findByNameIgnoreCase(String name);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Repo> findByName(String name);
}
