/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinic.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 */
@Entity
public class Doctor implements AbstractEntity, Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Имя не может быть пустым")
    @Pattern(regexp = "[a-zA-zа-яА-Я]*",message = "Имя может состоять из букв")
    private String firstName;

    @NotBlank(message = "Фамииля не может быть пустой")
    @Pattern(regexp = "[a-zA-zа-яА-Я]*",message = "Фамилия может состоять из букв")
    private String lastName;

    @Pattern(regexp = "[a-zA-zа-яА-Я]*",message = "Отчество может состоять из букв")
    private String patronymic;

    @NotBlank(message = "Специализация не должна быть пустой")
    private String specialization;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, String patronymic, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.specialization = specialization;
    }

    public String getNameWithSpec() {
        if (patronymic != null && !patronymic.isEmpty()) {
            return String.format("%s, %s %c. %c.", specialization, lastName, firstName.charAt(0), patronymic.charAt(0));
        } else {
            return String.format("%s, %s %c.", specialization, lastName, firstName.charAt(0));
        }
    }

    public Long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) &&
                firstName.equals(doctor.firstName) &&
                lastName.equals(doctor.lastName) &&
                patronymic.equals(doctor.patronymic) &&
                specialization.equals(doctor.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patronymic, specialization);
    }

    @Override
    public String toString() {
        return String.format("Doctor[id=%d, name=%s]", id, firstName);
    }
}
