package com.example.springproject.dao;
import com.example.springproject.domain.developer;
import org.springframework.data.repository.CrudRepository;
public interface developerDao extends CrudRepository<developer, Integer> {
}
