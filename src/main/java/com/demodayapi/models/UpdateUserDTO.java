package com.demodayapi.models;

import jakarta.validation.constraints.Size;

public class UpdateUserDTO {
    @Size(min = 3, message = "O nome de usuário deve ter no mínimo 3 caracteres.")
    private String name;

    private String email;

    @Size(min = 11, max = 11, message = "O CPF deve possuir 11 dígitos.")
    private String cpf;

    private String university;

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


    // Getters and Setters
}
