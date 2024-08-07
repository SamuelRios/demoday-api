package com.demodayapi.models;
import com.demodayapi.enums.UserStatusEnum;
import com.demodayapi.enums.UserTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Users")
public class User {

    @Id()
    private String id;

    @Column(nullable=false, length = 200)
    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 3, message = "O nome de usuário deve ter no mínimo 3 caracteres.")
    private String name;

    @Column(nullable=false, length = 45, unique=true)
    @NotBlank(message = "Email é obrigatório.")
    private String email;

    @Column(nullable=false, length = 45, unique=true)
    @Size(min = 11, max = 11, message = "O CPF deve possuir 11 dígitos.")
    @NotBlank(message = "CPF é obrigatório.")
    private String cpf;

    
    @Column(nullable=true, length = 45)
    private String university;
    
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo é obrigatório.")
    private UserTypeEnum type;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    

    @Transient()
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
    
    public UserTypeEnum getType() {
        return this.type;
    }

    public void setType(String type) {
        if(type != null)
            this.type = UserTypeEnum.valueOf(type);
    }

    public UserStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        if(status != null)
            this.status = UserStatusEnum.valueOf(status);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
