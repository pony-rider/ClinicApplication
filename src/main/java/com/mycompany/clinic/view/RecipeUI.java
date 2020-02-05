package com.mycompany.clinic.view;

import com.mycompany.clinic.entity.Patient;
import com.mycompany.clinic.entity.Priority;
import com.mycompany.clinic.entity.Recipe;
import com.mycompany.clinic.filter.Filter;
import com.mycompany.clinic.filter.FilterChain;
import com.mycompany.clinic.services.DoctorService;
import com.mycompany.clinic.services.PatientService;
import com.mycompany.clinic.services.RecipeService;
import com.vaadin.annotations.Title;
import com.vaadin.data.Binder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumSet;

@SpringUI(path = "/")
@Title("Рецепты")
public class RecipeUI extends UI {
    private TextField descriptionFilterTextField = new TextField("Описание");
    private ComboBox<Patient> patientFilterCombobox;
    private ComboBox<Priority> priorityFilterCombobox;
    private EntityPanel<Recipe> recipePanel;
    private Grid<Recipe> recipeGrid;
    private FilterChain<Recipe> filterChain;
    private EntityDialog<Recipe> recipeDialog;

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        setContent(content);
        content.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        content.setSpacing(false);
        Menu menu = new Menu();
        content.addComponent(menu);
        content.setExpandRatio(menu, 0f);
        Label label = new Label("<h2>Список рецептов</h2>", ContentMode.HTML);
        content.addComponent(label);
        recipeGrid = new Grid<>(Recipe.class);
        recipeGrid.removeColumn("patient");
        recipeGrid.removeColumn("doctor");
        recipeGrid.addColumn(r -> r.getPatient().getName()).setCaption("Пациент")
                  .setId("patient");
        recipeGrid.addColumn(r -> r.getDoctor().getNameWithSpec()).setCaption("Врач")
                  .setId("doctor");
        recipeGrid.getColumn("id").setCaption("ID").setWidth(80);
        recipeGrid.getColumn("description").setCaption("Описание").setExpandRatio(2);
        recipeGrid.getColumn("creationDate").setCaption("Дата создания");
        recipeGrid.getColumn("priority").setCaption("Приоритет");
        recipeGrid.getColumn("validityDays").setCaption("Срок действия");
        recipeGrid.setColumnOrder("id", "description", "patient", "doctor", "priority",
                "creationDate", "validityDays");
        recipePanel = new EntityPanel<>(recipeGrid, recipeService);
        HorizontalLayout filterPanel = constructFiltersPanel();
        initFilters();
        content.addComponent(filterPanel);
        content.setExpandRatio(filterPanel, 0f);
        content.addComponent(recipePanel);
        content.setExpandRatio(recipePanel, 1f);
        content.setComponentAlignment(filterPanel, Alignment.BOTTOM_LEFT);
        RecipeForm recipeForm = new RecipeForm(patientService, doctorService);

        recipeDialog = new EntityDialog<>(Recipe.class, recipeForm, recipePanel);
        Binder<Recipe> binder = recipeDialog.getBinder();
        binder.forField(recipeForm.getValidityDaysSlider())
              .withConverter(Double::intValue, Integer::doubleValue)
              .bind("validityDays");
        recipeDialog.center();
        recipePanel.addListenerToAddButton(event -> showDialog());
        recipePanel.addListenerToEditButton(
                event -> showDialog(recipePanel.getSelectedEntity()));
    }

    private HorizontalLayout constructFiltersPanel() {
        HorizontalLayout filterPanel = new HorizontalLayout();
        filterPanel.setMargin(new MarginInfo(false, true));
        filterPanel.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

        priorityFilterCombobox =
                new ComboBox<>("Приоритет", EnumSet.allOf(Priority.class));
        priorityFilterCombobox.setEmptySelectionCaption("Любой");
        descriptionFilterTextField.setPlaceholder("описание...");
        patientFilterCombobox = new ComboBox<>("Пациент", patientService.getAll());
        patientFilterCombobox.setEmptySelectionCaption("Все");
        patientFilterCombobox.setItemCaptionGenerator(Patient::getName);

        Button applyFiltersButton = new Button("Применить");
        Button disableFiltersButton = new Button("Отменить");
        filterPanel.addComponents(descriptionFilterTextField, patientFilterCombobox,
                priorityFilterCombobox);
        filterPanel.addComponents(applyFiltersButton, disableFiltersButton);
        applyFiltersButton.addClickListener(e -> {
            applyFiltersButton.setEnabled(false);
            disableFiltersButton.setEnabled(true);
            applyFilters();
        });
        disableFiltersButton.addClickListener(e -> {
            applyFiltersButton.setEnabled(true);
            disableFiltersButton.setEnabled(false);
            disableFilters();
        });
        disableFiltersButton.setEnabled(false);
        return filterPanel;
    }

    private void initFilters() {
        Filter<Recipe, String> descriptionFilter = new Filter<>(Recipe::getDescription,
                descriptionFilterTextField.getValue(),
                (desc, filterValue) -> filterValue.isEmpty() || desc
                        .contains(filterValue));
        Filter<Recipe, Patient> patientFilter =
                new Filter<>(Recipe::getPatient, patientFilterCombobox.getValue(),
                        (patient, filterValue) -> patient.equals(filterValue));
        Filter<Recipe, Priority> priorityFilter = new Filter<>(Recipe::getPriority,
                priorityFilterCombobox.getValue(), (priority, filterValue) ->
                filterValue != null && priority.equals(filterValue));

        descriptionFilterTextField.addValueChangeListener(e -> {
            descriptionFilter.setFilterValue(e.getValue());
            recipePanel.refreshGrid();
        });
        patientFilterCombobox.addValueChangeListener(e -> {
            patientFilter.setFilterValue(e.getValue());
            recipePanel.refreshGrid();
        });
        priorityFilterCombobox.addValueChangeListener(e -> {
            priorityFilter.setFilterValue(e.getValue());
            recipePanel.refreshGrid();
        });
        filterChain = new FilterChain(patientFilter)
                .addFilter(priorityFilter).addFilter(descriptionFilter);
    }

    private void applyFilters() {
        recipePanel.setGridFilter(recipe -> filterChain.filter(recipe));
        recipePanel.refreshGrid();
    }

    private void disableFilters() {
        recipePanel.setGridFilter(null);
        recipePanel.refreshGrid();
    }

    private void showDialog(Recipe e) {
        recipeDialog.setEntity(e);
        addWindow(recipeDialog);
    }

    private void showDialog() {
        recipeDialog.setEntity(new Recipe());
        addWindow(recipeDialog);
    }
}
