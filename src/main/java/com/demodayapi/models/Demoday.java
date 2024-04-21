package com.demodayapi.models;
import java.time.LocalDate;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="demoday")
public class Demoday {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false, length = 255)
    @NotBlank(message = "Nome do damoday é obrigatório.")
    @Size(min = 5, message = "O nome ddo damoday deve ter no mínimo 5 caracteres.")
    private String name;

    @Column(nullable=false,columnDefinition = "INT")
    private int year;

	@Future
	@NotNull
    @Column(columnDefinition = "DATE")
    private LocalDate phaseOneInit ;

	@Future
	@NotNull
    @Column(columnDefinition = "DATE")
    private LocalDate phaseOneEnd ;

	@Future
    @Column(columnDefinition = "DATE")
    private LocalDate phaseTwoInit ;

    @Future
    @Column( columnDefinition = "DATE")
    private LocalDate phaseTwoEnd ;

	@Future
    @Column( columnDefinition = "DATE")
    private LocalDate phaseThreeInit ;

	@Future
    @Column( columnDefinition = "DATE")
    private LocalDate phaseThreeEnd ;

	@Future
    @Column(columnDefinition = "DATE")
    private LocalDate phaseFourInit ;

    @Future
    @Column(columnDefinition = "DATE")
    private LocalDate phaseFourEnd ;


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


	public LocalDate getPhaseOneInit() {
		return phaseOneInit;
	}


	public void setPhaseOneInit(LocalDate phaseOneInit) {
		this.phaseOneInit = phaseOneInit;
	}


	public LocalDate getPhaseOneEnd() {
		return phaseOneEnd;
	}


	public void setPhaseOneEnd(LocalDate phaseOneEnd) {
		this.phaseOneEnd = phaseOneEnd;
	}


	public LocalDate getPhaseTwoInit() {
		return phaseTwoInit;
	}


	public void setPhaseTwoInit(LocalDate phaseTwoInit) {
		this.phaseTwoInit = phaseTwoInit;
	}


	public LocalDate getPhaseTwoEnd() {
		return phaseTwoEnd;
	}


	public void setPhaseTwoEnd(LocalDate phaseTwoEnd) {
		this.phaseTwoEnd = phaseTwoEnd;
	}


	public LocalDate getPhaseThreeInit() {
		return phaseThreeInit;
	}


	public void setPhaseThreeInit(LocalDate phaseThreeInit) {
		this.phaseThreeInit = phaseThreeInit;
	}


	public LocalDate getPhaseThreeEnd() {
		return phaseThreeEnd;
	}


	public void setPhaseThreeEnd(LocalDate phaseThreeEnd) {
		this.phaseThreeEnd = phaseThreeEnd;
	}


	public LocalDate getPhaseFourInit() {
		return phaseFourInit;
	}


	public void setPhaseFourInit(LocalDate phaseFourInit) {
		this.phaseFourInit = phaseFourInit;
	}


	public LocalDate getPhaseFourEnd() {
		return phaseFourEnd;
	}


	public void setPhaseFourEnd(LocalDate phaseFourEnd) {
		this.phaseFourEnd = phaseFourEnd;
	}


 

    

}
