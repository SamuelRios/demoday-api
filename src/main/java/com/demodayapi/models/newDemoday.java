package com.demodayapi.models;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="demoday")
public class newDemoday {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false, length = 255)
    @NotBlank(message = "Nome do damoday é obrigatório.")
    @Size(min = 5, message = "O nome ddo damoday deve ter no mínimo 5 caracteres.")
    private String name;

    @Column(nullable=false,columnDefinition = "INT")
    private int year;

    @NotNull
    @Column(nullable=false, columnDefinition = "DATE")
    private Date phaseOneInit ;

    @NotNull
    @Column(nullable=false, columnDefinition = "DATE")
    private Date phaseOneEnd ;


    @Column(nullable=false, columnDefinition = "DATE")
    private Date phaseTwoInit ;

   
    @Column(nullable=false, columnDefinition = "DATE")
    private Date phaseTwoEnd ;

    @Column(nullable=false, columnDefinition = "DATE")
    private Date phaseThreeInit ;

   
    @Column(nullable=false, columnDefinition = "DATE")
    private Date phaseThreeEnd ;

    @Column(nullable=false, columnDefinition = "DATE")
    private Date phaseFourInit ;

   
    @Column(nullable=false, columnDefinition = "DATE")
    private Date phaseFourEnd ;


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


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public Date getPhaseOneInit() {
		return phaseOneInit;
	}


	public void setPhaseOneInit(Date phaseOneInit) {
		this.phaseOneInit = phaseOneInit;
	}


	public Date getPhaseOneEnd() {
		return phaseOneEnd;
	}


	public void setPhaseOneEnd(Date phaseOneEnd) {
		this.phaseOneEnd = phaseOneEnd;
	}


	public Date getPhaseTwoInit() {
		return phaseTwoInit;
	}


	public void setPhaseTwoInit(Date phaseTwoInit) {
		this.phaseTwoInit = phaseTwoInit;
	}


	public Date getPhaseTwoEnd() {
		return phaseTwoEnd;
	}


	public void setPhaseTwoEnd(Date phaseTwoEnd) {
		this.phaseTwoEnd = phaseTwoEnd;
	}


	public Date getPhaseThreeInit() {
		return phaseThreeInit;
	}


	public void setPhaseThreeInit(Date phaseThreeInit) {
		this.phaseThreeInit = phaseThreeInit;
	}


	public Date getPhaseThreeEnd() {
		return phaseThreeEnd;
	}


	public void setPhaseThreeEnd(Date phaseThreeEnd) {
		this.phaseThreeEnd = phaseThreeEnd;
	}


	public Date getPhaseFourInit() {
		return phaseFourInit;
	}


	public void setPhaseFourInit(Date phaseFourInit) {
		this.phaseFourInit = phaseFourInit;
	}


	public Date getPhaseFourEnd() {
		return phaseFourEnd;
	}


	public void setPhaseFourEnd(Date phaseFourEnd) {
		this.phaseFourEnd = phaseFourEnd;
	}


 

    

}
