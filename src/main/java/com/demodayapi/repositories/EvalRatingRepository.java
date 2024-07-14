package com.demodayapi.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demodayapi.models.EvalRating;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface EvalRatingRepository extends CrudRepository<EvalRating, Integer> {
    List<EvalRating> findByUserAndProject(User user, Project project);

    @Modifying
    @Transactional
    @Query("DELETE FROM EvalRating er WHERE er.user.id = :userId AND er.project.id = :projectId")
    void deleteByUserIdAndProjectId(@Param("userId") String userId, @Param("projectId") int projectId);
}
