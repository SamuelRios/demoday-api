package com.demodayapi.models;

import com.demodayapi.enums.PhaseEvalRateEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="eval_rating", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "project_id", "eval_criteria_id", "phase"})
})
public class EvalRating {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //id da avaliação do user

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Id do usuário é obrigatório.")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @NotNull(message = "Id do projeto é obrigatório.")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "eval_criteria_id", nullable = false)
    @NotNull(message = "Id do critério de avaliação é obrigatório.")
    private EvalCriteriaDemoday evalCriteria;

    @Column(nullable=false)
    @Min(value = 1, message = "O valor deve ser no mínimo {value}.")
    @Max(value = 5, message = "O valor deve ser no máximo {value}.")
    private Integer rate;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PhaseEvalRateEnum phase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public EvalCriteriaDemoday getEvalCriteria() {
        return evalCriteria;
    }

    public void setEvalCriteria(EvalCriteriaDemoday evalCriteria) {
        this.evalCriteria = evalCriteria;
    }

    public int getRate(){
        return this.rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getProjectId() {
        return this.project.getId();
    }

    public int getEvalCriteriaId(){
        return this.evalCriteria.getId();
    }

    public PhaseEvalRateEnum  getPhase() {
        return this.phase;
    }

    public void setPhase(PhaseEvalRateEnum phaseEvalRate) {
        this.phase = phaseEvalRate;
    }


}
