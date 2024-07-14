package com.demodayapi.repositories;

import org.springframework.stereotype.Repository;

import com.demodayapi.models.EvalCriteriaDemoday;
import com.demodayapi.models.EvalRating;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

@Repository
public interface EvalRatingRepository extends CrudRepository<EvalRating, Integer> {
    List<EvalRating> findByUserAndProjectAndEvalCriteria(User user, Project project, EvalCriteriaDemoday evalCriteria);
}
