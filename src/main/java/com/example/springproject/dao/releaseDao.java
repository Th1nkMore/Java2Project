package com.example.springproject.dao;
import com.example.springproject.domain.release;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Release dao.
 */
public interface releaseDao extends CrudRepository<release, Integer> {
    /**
     * Find by repo order by published at asc list.
     *
     * @param repo the repo
     * @return the list
     */
    List<release> findByRepoOrderByPublishedAtAsc(@NonNull String repo);

    /**
     * Find by repo list.
     *
     * @param repo the repo
     * @return the list
     */
    List<release> findByRepo(String repo);

}
