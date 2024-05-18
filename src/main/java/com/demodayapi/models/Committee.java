package com.demodayapi.models;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comission")
public class Committee {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "demoday_id")
    private Demoday demoday;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "committee_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Demoday getDemoday() {
        return demoday;
    }

    public void setDemoday(Demoday demoday) {
        this.demoday = demoday;
    }
 

}
 