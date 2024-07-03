package com.demodayapi.models;
import java.time.Year;
import java.util.List;
import com.demodayapi.enums.ProjectStatusEnum;
import com.demodayapi.enums.ProjectTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="projects")
public class Project {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private Integer  period;

    @Column(nullable=false, length = 255) 
    @NotBlank(message = "título é obrigatório.")
    private String title;

    @Column(nullable=false, length = 255)
    @Size(min = 10, max = 255, message = "O link deve conter no mínimo 10 caracteres.")
    @NotBlank(message = "O link do vídeo é obrigatório.")
    private String linkvideo;

    @Column(nullable=true, length = 255)
    @Size(min = 3, max = 255, message = "a disciplina deve conter no mínimo 3 caracteres.")
    @NotBlank(message = "O link do vídeo é obrigatório.")
    private String discipline;
    
    @Column(nullable=false, length = 255)
    @Size(min = 3, max = 255, message = "o nome do professor deve conter no mínimo 3 caracteres.")
    @NotBlank(message = "O nome do professor é obrigatório.")
    private String professor;

    @Column(nullable=true, length = 4, columnDefinition = "YEAR")
    private Year year;

    @Column(nullable=false, length = 500)
    @Size(min = 10, max = 500, message = "A descrição deve conter no mínimo 10 caracteres")
    @NotBlank(message = "A descrição é obrigatória.")
    private String description;
    

    @Column(length = 255)
    @Size(min = 0, max = 255, message = "O campo tecnologies deve conter no mínimo 4 caracteres")
    private String tecnologies;

    @Column(length = 255)
    @Size(min = 0, max = 255, message = "O campo tecnologies deve conter no mínimo 4 caracteres")
    private String linkdoc;
    
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private ProjectStatusEnum status;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private ProjectTypeEnum type;


    @ElementCollection
    @Column(name = "emails", nullable = true)
    private List<String> emails;
    

    @Column(length = 500)
    private String image;
       
    @ManyToOne
    @JoinColumn(name = "demoday_id")
    private Demoday demoday;

    @Column(length = 500)
    private String rejectionReason;

    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "É necessario ter um usuario cadastrado.")
    private User user;


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkvideo() {
        return linkvideo;
    }

    public void setLinkvideo(String linkvideo) {
        this.linkvideo = linkvideo;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getTecnologies() {
        return tecnologies;
    }

    public void setTecnologies(String tecnologies) {
        this.tecnologies = tecnologies;
    }

    public String getLinkdoc() {
        return linkdoc;
    }

    public void setLinkdoc(String linkdoc) {
        this.linkdoc = linkdoc;
    }

    public Demoday getDemoday() {
        return demoday;
    }

    public void setDemoday(Demoday demoday) {
        this.demoday = demoday;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public ProjectStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = ProjectStatusEnum.valueOf(status);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public ProjectTypeEnum getType() {
        return type;
    }

    public void setType(String type) {
        if(type != null)
            this.type = ProjectTypeEnum.valueOf(type);
    }

   

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public void setStatus(ProjectStatusEnum status) {
        this.status = status;
    }

 
    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}

   
