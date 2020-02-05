/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinic.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

/**
 *
 */
public class DoctorForm extends FormLayout {
    private TextField firstName = new TextField("Имя");
    private TextField lastName = new TextField("Фамилия");
    private TextField patronymic = new TextField("Отчество");
    private TextField specialization = new TextField("Специализация");

    public DoctorForm() {
        super();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);
        specialization.setRequiredIndicatorVisible(true);
        addComponents(firstName, lastName, patronymic, specialization);
        setWidth("350px");
        setCaptionAsHtml(true);
        setCaption("<h2>Врач</h2>");
    }
}
