
package com.demodayapi.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="acc_criteria")

public class AccCriteriaDemoday {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255)
    private String name;


    @Column(length = 255)
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "demoday_id")
     private Demoday demoday;


    public Demoday getDemoday() {
        return this.demoday;
    }

    public void setDemoday(Demoday demoday) {
        this.demoday = demoday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
    
}
