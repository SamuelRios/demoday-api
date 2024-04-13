package com.demodayapi.models;
import com.google.cloud.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="demoday")
public class newDemoday {

    @Id()
    private int id;

    @Column(nullable=false, length = 200)
    @NotBlank(message = "Nome do damoday é obrigatório.")
    @Size(min = 5, message = "O nome ddo damoday deve ter no mínimo 5 caracteres.")
    private String name;

    @Column(nullable=false, length = 4)
    @NotBlank(message = "Forneça um ano com 4 caracteres e maior que o ano atual")
    private int year;

    @Column(nullable=false, length = 10)
    @Size(min =8, max = 10, message = "Insira uma data válida")
    @NotBlank(message = "A data da fase 1 é obrigatória.")
    private Date phase_one_init;

    @Column(nullable=false, length = 10)
    @Size(min =8, max = 10, message = "Insira uma data válida")
    @NotBlank(message = "A data da fase 1 é obrigatória.")
    private Date phase_one_end;


    @Column(length = 10)
    @Size(min =8, max = 10, message = "Insira uma data válida")
    private Date phase_two_init;

    @Column(length = 10)
    @Size(min =8, max = 10, message = "Insira uma data válida")
    private Date phase_two_end;


    @Column(length = 10)
    @Size(min =8, max = 10, message = "Insira uma data válida")
    private Date phase_three_init;

    @Column(length = 10)
    @Size(min =8, max = 10, message = "Insira uma data válida")
    private Date phase_three_end;


    @Column(length = 10)
    @Size(min =8, max = 10, message = "Insira uma data válida")
    private Date phase_four_init;

    @Column(length = 10)
    @Size(min =8, max = 10, message = "Insira uma data válida")
    private Date phase_four_end;
    
    



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



    public int getYear () {
        return year;
    }

    public void setYear(int year) {
        this.year= year;
    }



    public Date get_phase_one_init() {
        return phase_one_init;
    }

    public void set_phase_one_init(Date phase_one_init) {
        this.phase_one_init = phase_one_init;
    }



    public Date get_phase_two_init() {
        return phase_two_init;
    }

    public void set_phase_two_init(Date phase_two_init) {
        this.phase_two_init = phase_two_init;
    }



    public Date get_phase_three_init() {
        return phase_three_init;
    }

    public void set_phase_trhee_init(Date phase_three_init) {
        this.phase_three_init = phase_three_init;
    }



    public Date get_four_three_init() {
        return phase_four_init;
    }

    public void set_phase_four_init(Date phase_four_init) {
        this.phase_four_init = phase_four_init;
    }



    public Date get_phase_one_end() {
        return phase_one_end;
    }
    
    public void set_phase_one_end(Date phase_one_end) {
        this.phase_one_end = phase_one_end;
    }


    
    public Date get_phase_two_end() {
        return phase_two_end;
    }
    
    public void set_phase_two_end(Date phase_two_end) {
        this.phase_two_end = phase_two_end;
    }


    
    public Date get_phase_three_end() {
        return phase_three_end;
    }
    
    public void set_phase_three_end(Date phase_three_end) {
        this.phase_three_end = phase_three_end;
    }
    


    public Date get_phase_four_end() {
        return phase_four_end;
    }
    
    
    public void set_phase_four_end(Date phase_four_end) {
        this.phase_four_end = phase_four_end;
    }





   
    
    

}
