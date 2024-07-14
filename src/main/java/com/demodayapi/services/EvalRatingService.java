package com.demodayapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.models.EvalCriteriaDemoday;
import com.demodayapi.models.EvalRating;
import com.demodayapi.models.EvalRatingRequest;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.repositories.EvalRatingRepository;

@Service
public class EvalRatingService {
    @Autowired
    EvalRatingRepository evalRatingRepository;

    public EvalRating updateEvalRating(int id, EvalRating updatedEvalRating) {
        return evalRatingRepository.findById(id).map(evalRating -> {
            evalRating.setRate(updatedEvalRating.getRate());
            return evalRatingRepository.save(evalRating);
        }).orElseThrow(() -> new RuntimeException("EvalRating not found with id " + id));
    }

    public boolean hasUserVotedProject(String userId, int projectId){
        System.out.println("has voted?");
        User user = new User();
        user.setId(userId);
        Project project = new Project();
        project.setId(projectId);
        List<EvalRating> evalRating =  this.evalRatingRepository.findByUserAndProject(user, project);
        if(evalRating != null && evalRating.size() > 0){
            return true;
        }
        return false;
    }

    public EvalRating saveOrUpdateEvalRating(EvalRating evalRating) {
        return evalRatingRepository.save(evalRating);
    }

    public void createNewEvalRating(String userId, int projectId, EvalRatingRequest evalRatingRequest){
        User user = new User();
        user.setId(userId);
        Project project = new Project();
        project.setId(projectId);
        EvalCriteriaDemoday evalCriteria = new EvalCriteriaDemoday();
        evalCriteria.setId(evalRatingRequest.getEvalCriteriaId());
        EvalRating myEvalRating = new EvalRating();
        myEvalRating.setUser(user);
        myEvalRating.setProject(project);
        myEvalRating.setEvalCriteria(evalCriteria);
        myEvalRating.setRate(evalRatingRequest.getRate());
        evalRatingRepository.save(myEvalRating);
    }

    public void deleteEvalRatingsByUserAndProject(String userId, int projectId){
        this.evalRatingRepository.deleteByUserIdAndProjectId(userId, projectId);
    }
}
