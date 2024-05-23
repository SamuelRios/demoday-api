package com.demodayapi.models;
import jakarta.persistence.Entity;
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
 