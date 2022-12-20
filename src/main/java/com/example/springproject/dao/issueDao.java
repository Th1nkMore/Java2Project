package com.example.springproject.dao;
import com.example.springproject.domain.issue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Issue dao.
 */
public interface issueDao extends CrudRepository<issue, Integer> {
    /**
     * Find by repo ignore case list.
     *
     * @param repo the repo
     * @return the list
     */
    List<issue> findByRepoIgnoreCase(String repo);
}
