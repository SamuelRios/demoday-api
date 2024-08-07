package com.demodayapi.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demodayapi.enums.PhaseEvalRateEnum;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.EvalCriteriaDemoday;
import com.demodayapi.models.EvalRating;
import com.demodayapi.models.EvalRatingRequest;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;
import com.demodayapi.repositories.EvalRatingRepository;

@Service
public class EvalRatingService {
    @Autowired
    private EvalRatingRepository evalRatingRepository;

    public EvalRating updateEvalRating(int id, EvalRating updatedEvalRating) {
        return evalRatingRepository.findById(id).map(evalRating -> {
            evalRating.setRate(updatedEvalRating.getRate());
            return evalRatingRepository.save(evalRating);
        }).orElseThrow(() -> new RuntimeException("EvalRating not found with id " + id));
    }

    public boolean hasUserVotedProject(String userId, int projectId, PhaseEvalRateEnum phaseEvalRate){
        System.out.println("has voted?");
        User user = new User();
        user.setId(userId);
        Project project = new Project();
        project.setId(projectId);
        List<EvalRating> evalRating =  this.evalRatingRepository.findByUserAndProjectAndPhase(user, project, phaseEvalRate);
        
        if(evalRating != null && evalRating.size() > 0){
            for(EvalRating eval: evalRating){
                System.out.println(eval.getId());
            }
            return true;
        }
        return false;
    }
    public List<EvalRating> projectEvaluatedbByUser(String iduser, int idDemoday) {
        return evalRatingRepository.projectevaluatedbyuser(iduser,idDemoday);
    }
    public EvalRating saveOrUpdateEvalRating(EvalRating evalRating) {
        return evalRatingRepository.save(evalRating);
    }

    public void createNewEvalRating(String userId, int projectId, EvalRatingRequest evalRatingRequest, PhaseEvalRateEnum phaseEvalRate){
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
        myEvalRating.setPhase(phaseEvalRate);
        evalRatingRepository.save(myEvalRating);
    }

    public void deleteEvalRatingsByUserAndProjectAndPhase(String userId, int projectId, PhaseEvalRateEnum phaseEvalRate){
        this.evalRatingRepository.deleteByUserIdAndProjectIdAndPhase(userId, projectId, phaseEvalRate);
    }

    public List<EvalRating> findAll(){
        return evalRatingRepository.findAll();
    }

    public List<EvalRating> findByDemodayAndPhase(int demodayId, PhaseEvalRateEnum phaseEvalRate){
        return evalRatingRepository.findByDemodayIdAndPhase(demodayId, phaseEvalRate);
    }


    public Map<Integer, Double> getAllProjectRatings() {
        List<EvalRating> evalRatings = findAll();
        return evalRatings.stream()
                .collect(Collectors.groupingBy(
                        EvalRating::getProjectId,
                        Collectors.averagingDouble(EvalRating::getRate)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<Integer, Double> getProjectRatingsByDemodayAndPhase(int demodayId, PhaseEvalRateEnum phaseEvalRate) {
        List<EvalRating> evalRatings = findByDemodayAndPhase(demodayId, phaseEvalRate);
        return evalRatings.stream()
                .collect(Collectors.groupingBy(
                        EvalRating::getProjectId,
                        Collectors.averagingDouble(EvalRating::getRate)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
