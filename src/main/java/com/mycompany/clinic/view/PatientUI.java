package com.mycompany.clinic.view;

import com.mycompany.clinic.entity.Patient;
import com.mycompany.clinic.services.PatientService;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "/patients")
@Title("Пациенты")
@StyleSheet("style.css")
public class PatientUI extends UI {

    @Autowired
    private PatientService patientService;
    private EntityDialog<Patient> patientDialog;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(true);
        content.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        content.setSpacing(false);
        setContent(content);
        Menu menu = new Menu();
        content.addComponent(menu);
        content.setExpandRatio(menu, 0f);

        Label label = new Label("<h2>Список пациетов</h2>", ContentMode.HTML);
        content.addComponent(label);
        Grid<Patient> patientGrid = new Grid<>(Patient.class);
        patientGrid.removeColumn("name");
        patientGrid.getColumn("id").setCaption("ID");
        patientGrid.getColumn("firstName").setCaption("Имя");
        patientGrid.getColumn("lastName").setCaption("Фамилия");
        patientGrid.getColumn("patronymic").setCaption("Отчество");
        patientGrid.setColumnOrder("id", "lastName", "firstName", "patronymic");
        EntityPanel<Patient> entityPanel = new EntityPanel<>(patientGrid, patientService);
        content.addComponent(entityPanel);
        content.setExpandRatio(entityPanel, 1f);
        patientDialog = new EntityDialog<>(Patient.class, new PatientForm(), entityPanel);
        patientDialog.center();
        entityPanel.addListenerToAddButton(event -> showDialog());
        entityPanel.addListenerToEditButton(event ->
                showDialog(entityPanel.getSelectedEntity()));
    }

    private void showDialog(Patient e) {
        patientDialog.setEntity(e);
        addWindow(patientDialog);
    }

    private void showDialog() {
        patientDialog.setEntity(new Patient());
        addWindow(patientDialog);
    }
}
