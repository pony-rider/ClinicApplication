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
public class Patient implements AbstractEntity, Serializable {

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

    @Pattern(regexp= "^(\\s*)|(\\d{10})$", message = "Телефон должен состоять из 10 цифр")
    private String phone;

    public Patient() {}

    public Patient(String firstName, String lastName, String patronymic,
            String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phone = phone;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        if (patronymic != null && !patronymic.isEmpty()) {
            return String.format("%s %c. %c.", lastName, firstName.charAt(0), patronymic.charAt(0));
        } else {
            return String.format("%s %c.", lastName, firstName.charAt(0));
        }

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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) &&
                firstName.equals(patient.firstName) &&
                lastName.equals(patient.lastName) &&
                patronymic.equals(patient.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patronymic);
    }

    @Override
    public String toString() {
        return String.format("Patient[id=%d, name=%s]", id, firstName);
    }
}
