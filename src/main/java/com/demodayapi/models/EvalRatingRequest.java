package com.demodayapi.models;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EvalRatingRequest {

    @NotNull(message = "O ID do critério de avaliação é obrigatório.")
    private Integer evalCriteriaId;


    @Min(value = 1, message = "O valor deve ser no mínimo {value}.")
    @Max(value = 5, message = "O valor deve ser no máximo {value}.")
    @NotNull(message = "A valor da avaliação é obrigatório.")
    private Integer rate;

    public Integer getEvalCriteriaId() {
        return evalCriteriaId;
    }

    public void setEvalCriteriaId(Integer evalCriteriaId) {
        this.evalCriteriaId = evalCriteriaId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}