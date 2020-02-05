/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.clinic.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 */
@Entity
public class Recipe implements AbstractEntity, Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Поле пациент не может быть пустым")
    @ManyToOne
    private Patient patient;

    @NotNull(message = "Поле врач не может быть пустым")
    @ManyToOne
    private Doctor doctor;
    private LocalDate creationDate;

    @NotNull(message = "Срок действия не может быть пустым")
    private int validityDays = 1;

    @NotNull(message = "Приоритет не может быть пустым")
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.NORMAL;

    public Recipe() {}

    public Recipe(String description, Patient patient, Doctor doctor, int validityDays, Priority priority) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.validityDays = validityDays;
        this.priority = priority;
    }

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(int validityDays) {
        this.validityDays = validityDays;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

}
