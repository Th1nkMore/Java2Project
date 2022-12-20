package com.example.springproject.dao;
import com.example.springproject.domain.commit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * The interface Commit dao.
 */
public interface commitDao extends CrudRepository<commit, Integer> {
    /**
     * Find by repo and date between list.
     *
     * @param repo      the repo
     * @param dateStart the date start
     * @param dateEnd   the date end
     * @return the list
     */
    List<commit> findByRepoAndDateBetween(String repo, Timestamp dateStart, Timestamp dateEnd);

    /**
     * Find by repo list.
     *
     * @param repo the repo
     * @return the list
     */
    List<commit> findByRepo(@NonNull String repo);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<commit> findById(int id);
}
