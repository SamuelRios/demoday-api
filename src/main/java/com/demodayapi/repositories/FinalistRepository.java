package com.demodayapi.repositories;

import com.demodayapi.models.Finalist;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface FinalistRepository extends CrudRepository<Finalist, Integer>{
    List<Finalist> findAll();
    void deleteByProjectId(Integer projectId);
    Finalist findByProjectId(Integer projectId);
}
