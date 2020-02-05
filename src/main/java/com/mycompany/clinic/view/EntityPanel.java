/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clinic.view;

import com.mycompany.clinic.entity.AbstractEntity;
import com.mycompany.clinic.services.EntityService;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.Map;

/**
 * @param <E>
 */
public class EntityPanel<E extends AbstractEntity> extends VerticalLayout {
    public EntityService<E> service;
    private Grid<E> entityGrid;
    private Button addButton = new Button("Добавить");
    private Button editButton = new Button("Изменить");
    private Button deleteButton = new Button("Удалить");
    private Map<Long, E> entityMap = new HashMap<>();
    private ListDataProvider<E> gridDataProvider;
    private HorizontalLayout buttonGroup = new HorizontalLayout();

    public EntityPanel(Class<E> type, EntityService<E> service) {
        this(new Grid<>(type), service);
    }

    public EntityPanel(Grid<E> grid, EntityService<E> service) {
        this.service = service;
        entityGrid = grid;
        service.getAll().forEach(e -> entityMap.put(e.getId(), e));
        gridDataProvider = new ListDataProvider<>(entityMap.values());
        entityGrid.setDataProvider(gridDataProvider);
        initButtonGroup(this);
        addComponent(entityGrid);
        setSizeFull();
        setExpandRatio(entityGrid, 1);
        entityGrid.setSizeFull();
        enableButtons(false);
        entityGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                enableButtons(false);
            } else {
                enableButtons(true);
            }
        });
    }

    public void setGridFilter(SerializablePredicate<E> predicate) {
        gridDataProvider.setFilter(predicate);
    }

    public void refreshGrid() {
        gridDataProvider.refreshAll();
    }

    public void addCustomButtonToButtonGroup(Button button) {
        buttonGroup.addComponent(button);
    }

    public void removeCustomButtonFromButtonGroup(Button button) {
        buttonGroup.removeComponent(button);
    }

    private void initButtonGroup(Layout mainLayout) {
        buttonGroup.addComponents(addButton, editButton, deleteButton);
        mainLayout.addComponent(buttonGroup);
        deleteButton.addClickListener(e -> delete());
    }

    private void enableButtons(boolean enable) {
        editButton.setEnabled(enable);
        deleteButton.setEnabled(enable);
    }


    public void addListenerToAddButton(Button.ClickListener clickListener) {
        addButton.addClickListener(clickListener);
    }

    public void addListenerToEditButton(Button.ClickListener clickListener) {
        editButton.addClickListener(clickListener);
    }

    public void addSelectionListener(HasValue.ValueChangeListener<E> valueChangeListener) {
        entityGrid.asSingleSelect().addValueChangeListener(valueChangeListener);
    }

    private void delete() {
        E entity = getSelectedEntity();
        try {
            service.delete(entity);
            entityMap.remove(entity.getId());
            entityGrid.getDataProvider().refreshAll();
        } catch (DataIntegrityViolationException ex) {
            showError("Нельзя удалить сущность, имеющую связи с другими сущностями");
        }
    }

    private void showError(String message) {
        Notification.show("Ошибка:\n", message, Notification.Type.ERROR_MESSAGE);

    }

    private void updateEntity(E entity) {
        try {
            service.update(entity);
            entityMap.put(entity.getId(), entity);
            refreshGrid();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    public E getSelectedEntity() {
        return entityGrid.asSingleSelect().getValue();
    }

    private void createEntity(E entity) {
        try {
            service.create(entity);
            entityMap.put(entity.getId(), entity);
            refreshGrid();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    public void updateGrid(E entity) {
        if (entity.getId() != null) {
            updateEntity(entity);
        } else {
            createEntity(entity);
        }
    }
}
