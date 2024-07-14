package com.demodayapi.models;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EvalRatingRequest {
    @NotNull(message = "projectId é obrigatório")
    private Integer projectId;

    @NotNull(message = "evalCriteriaId é obrigatório")
    private Integer evalCriteriaId;


    @Min(value = 1, message = "O valor deve ser no mínimo {value}.")
    @Max(value = 5, message = "O valor deve ser no máximo {value}.")
    private int rate;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getEvalCriteriaId() {
        return evalCriteriaId;
    }

    public void setEvalCriteriaId(Integer evalCriteriaId) {
        this.evalCriteriaId = evalCriteriaId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}