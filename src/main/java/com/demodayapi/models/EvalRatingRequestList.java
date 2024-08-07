package com.demodayapi.models;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EvalRatingRequestList {
    
    @Size(min = 1, message = "O array deve conter pelo menos uma avaliação.")
    @Valid
    private List<EvalRatingRequest> evalRatings;

    @NotNull(message = "Id do projeto é obrigatório")
    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<EvalRatingRequest> getEvalRatings() {
        return this.evalRatings;
    }

    public void setEvalRatings(List<EvalRatingRequest> evalRatings) {
        this.evalRatings = evalRatings;
    }



    
}
