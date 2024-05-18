package com.demodayapi.models;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
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

	@FutureOrPresent
	@NotNull
    @Column(columnDefinition = "DATE")
    private LocalDate phaseOneInit ;

	@FutureOrPresent
	@NotNull
    @Column(columnDefinition = "DATE")
    private LocalDate phaseOneEnd ;

	@FutureOrPresent
    @Column(columnDefinition = "DATE")
    private LocalDate phaseTwoInit ;

    @FutureOrPresent
    @Column( columnDefinition = "DATE")
    private LocalDate phaseTwoEnd ;

	@FutureOrPresent
    @Column( columnDefinition = "DATE")
    private LocalDate phaseThreeInit ;

	@FutureOrPresent
    @Column( columnDefinition = "DATE")
    private LocalDate phaseThreeEnd ;

	@FutureOrPresent
    @Column(columnDefinition = "DATE")
    private LocalDate phaseFourInit ;

    @FutureOrPresent
    @Column(columnDefinition = "DATE")
    private LocalDate phaseFourEnd ;

	 
     

	

	@OneToMany(mappedBy ="demoday",cascade=CascadeType.ALL)
    private List<AccCriteriaDemoday> accCriteriaDemoday ;
	
    @OneToMany(mappedBy ="demoday",cascade=CascadeType.ALL)
    private List<EvalCriteriaDemoday> evalCriteriaDemoday ;
    
	@OneToMany(mappedBy ="demoday",cascade=CascadeType.ALL)
    private List<Project> project ;

	@PrePersist
	public void prePersist(){
	this.accCriteriaDemoday.forEach( i -> i.setDemoday(this));
	this.evalCriteriaDemoday.forEach( i -> i.setDemoday(this));
	

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


	public List <AccCriteriaDemoday> getAccCriteriaDemoday() {
		return  accCriteriaDemoday;
	}


	public  void setAccCriteriaDemoday (List<AccCriteriaDemoday> accCriteriaDemoday) {
		this.accCriteriaDemoday = accCriteriaDemoday;
	}


	public void setEvalCriteriaDemoday(List<EvalCriteriaDemoday> evalCriteriaDemoday) {
		this.evalCriteriaDemoday = evalCriteriaDemoday;
	}


	public List <EvalCriteriaDemoday> getEvalCriteriaDemoday() {
		return  evalCriteriaDemoday;
	}


	 


	


	 



   
     

}
 
