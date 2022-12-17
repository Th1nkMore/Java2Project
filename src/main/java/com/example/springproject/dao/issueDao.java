package com.example.springproject.dao;
import com.example.springproject.domain.issue;
import org.springframework.data.repository.CrudRepository;
public interface issueDao extends CrudRepository<issue, Integer> {
}
