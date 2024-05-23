package com.demodayapi.models;
import java.util.List;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="committee")
public class Committee {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToOne()
    @JoinColumn(name = "demoday")
    private Demoday demoday;

    public Demoday getDemoday() {
        return demoday;
    }

    public void setDemoday(Demoday demoday) {
        this.demoday = demoday;
    }

}
 