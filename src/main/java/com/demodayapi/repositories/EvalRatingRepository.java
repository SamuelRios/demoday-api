package com.demodayapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demodayapi.enums.PhaseEvalRateEnum;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.EvalRating;
import com.demodayapi.models.Project;
import com.demodayapi.models.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface EvalRatingRepository extends JpaRepository<EvalRating, Integer> {
    List<EvalRating> findByUserAndProjectAndPhase(User user, Project project, PhaseEvalRateEnum phaseEvalRate);


    @Query("SELECT er FROM EvalRating er WHERE er.project.demoday.id = :demodayId AND er.phase = :phase")
    List<EvalRating> findByDemodayIdAndPhase(@Param("demodayId") int demodayId, @Param("phase") PhaseEvalRateEnum phase);

    @Modifying
    @Transactional
    @Query("DELETE FROM EvalRating er WHERE er.user.id = :userId AND er.project.id = :projectId AND er.phase = :phase")
    void deleteByUserIdAndProjectIdAndPhase(@Param("userId") String userId, @Param("projectId") int projectId, @Param("phase") PhaseEvalRateEnum phase);
}
