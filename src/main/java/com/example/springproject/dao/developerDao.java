package com.example.springproject.dao;
import com.example.springproject.domain.developer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Developer dao.
 */
public interface developerDao extends CrudRepository<developer, Integer> {
    /**
     * Find by repo order by contributions desc list.
     *
     * @param repo the repo
     * @return the list
     */
    List<developer> findByRepoOrderByContributionsDesc(String repo);
}
