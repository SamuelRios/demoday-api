package com.demodayapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "finalists", uniqueConstraints = {@UniqueConstraint(columnNames = {"demoday_id", "project_id"})})
public class Finalist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "demoday_id", nullable = false)
    @NotNull(message = "Demoday é obrigatório.")
    private Demoday demoday;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @NotNull(message = "Project é obrigatório.")
    private Project project;

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Demoday getDemoday() {
        return demoday;
    }

    public void setDemoday(Demoday demoday) {
        this.demoday = demoday;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}