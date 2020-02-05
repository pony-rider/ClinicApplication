package com.mycompany.clinic.view;

import com.mycompany.clinic.entity.Doctor;
import com.mycompany.clinic.services.DoctorService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;


@SpringUI(path = "/doctors")
@Title("Врачи")
@Theme(ValoTheme.THEME_NAME)
public class DoctorUI extends UI {

    @Autowired
    private DoctorService doctorService;
    private EntityDialog<Doctor> entityDialog;
    private EntityPanel<Doctor> entityPanel;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(true);
        setContent(content);
        content.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        content.setSpacing(false);
        Menu menu = new Menu();
        content.addComponent(menu);
        content.setExpandRatio(menu, 0f);
        Label label = new Label("<h2>Список врачей</h2>", ContentMode.HTML);
        content.addComponent(label);
        Grid<Doctor> entityGrid = new Grid<>(Doctor.class);
        entityGrid.removeColumn("nameWithSpec");
        entityGrid.getColumn("id").setCaption("ID");
        entityGrid.getColumn("firstName").setCaption("Имя");
        entityGrid.getColumn("lastName").setCaption("Фамилия");
        entityGrid.getColumn("patronymic").setCaption("Отчество");
        entityGrid.getColumn("specialization").setCaption("Специализация");
        entityGrid.setColumnOrder("id", "lastName", "firstName", "patronymic", "specialization");
        entityPanel = new EntityPanel<>(entityGrid, doctorService);
        content.addComponent(entityPanel);
        content.setExpandRatio(entityPanel, 1f);
        entityDialog = new EntityDialog<>(Doctor.class, new DoctorForm(), entityPanel);
        entityDialog.center();
        entityPanel.addListenerToAddButton(event -> showDialog());
        entityPanel.addListenerToEditButton(event -> showDialog(entityPanel.getSelectedEntity()));

        Button showStatsButton = new Button("Показать статистику");
        showStatsButton.setEnabled(false);

        entityPanel.addSelectionListener(e -> showStatsButton.setEnabled(e != null));
        showStatsButton.addClickListener(e -> showStats());
        entityPanel.addCustomButtonToButtonGroup(showStatsButton);
    }

    private void showStats() {
        Doctor doctor = entityPanel.getSelectedEntity();
        long recipesCount = doctorService.getRecipesCount(doctor);
        Notification.show("", String.format("%s кол-во рецептов: %d",
                doctor.getNameWithSpec(), recipesCount), Notification.Type.HUMANIZED_MESSAGE);
    }

    private void showDialog(Doctor e) {
        entityDialog.setEntity(e);
        addWindow(entityDialog);
    }

    private void showDialog() {
        entityDialog.setEntity(new Doctor());
        addWindow(entityDialog);
    }
}
