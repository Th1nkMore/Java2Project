package com.example.springproject.dao;
import com.example.springproject.domain.commit;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface commitDao extends CrudRepository<commit, Integer> {

    Optional<commit> findById(int id);
}
