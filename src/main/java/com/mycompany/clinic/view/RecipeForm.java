/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinic.view;

import com.mycompany.clinic.entity.Doctor;
import com.mycompany.clinic.entity.Patient;
import com.mycompany.clinic.entity.Priority;
import com.mycompany.clinic.services.DoctorService;
import com.mycompany.clinic.services.PatientService;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;

import java.util.EnumSet;

/**
 *
 */
public class RecipeForm extends FormLayout {
    private TextArea description = new TextArea("Описание");
    private ComboBox<Patient> patient;
    private ComboBox<Doctor> doctor;
    private ComboBox<Priority> priority = new ComboBox<>("Приоритет", EnumSet.allOf(Priority.class));
    private Slider validityDaysSlider = new Slider("Срок действия", 1, 100);
    private String fieldWidth = "90%";

    public RecipeForm(PatientService patientService, DoctorService doctorService) {
        super();
        description.setWidth(fieldWidth);
        description.setRequiredIndicatorVisible(true);
        description.setWordWrap(true);
        description.setRows(3);
        patient = new ComboBox<>("Пациент", patientService.getAll());
        patient.setItemCaptionGenerator(Patient::getName);
        patient.setWidth(fieldWidth);
        doctor = new ComboBox<>("Врач", doctorService.getAll());
        doctor.setWidth(fieldWidth);
        doctor.setItemCaptionGenerator(Doctor::getNameWithSpec);
        validityDaysSlider.setOrientation(SliderOrientation.HORIZONTAL);
        validityDaysSlider.setWidth(fieldWidth);
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(description, patient, doctor, priority, validityDaysSlider);
        setWidth("450px");
        setCaptionAsHtml(true);
        setCaption("<h2>Рецепт</h2>");
    }

    public Slider getValidityDaysSlider() {
        return validityDaysSlider;
    }
}
