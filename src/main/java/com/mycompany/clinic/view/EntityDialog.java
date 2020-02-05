/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.clinic.view;


import com.mycompany.clinic.entity.AbstractEntity;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 */
public class EntityDialog<E extends AbstractEntity> extends Window {
    private FormLayout formLayout;
    private BeanValidationBinder<E> binder;
    private Button okButton = new Button("OK");
    private Button cancelButton = new Button("Cancel");
    private EntityPanel<E> entityPanel;
    private E entity;

    public EntityDialog(Class<E> type, FormLayout formLayout,
            EntityPanel<E> entityPanel) {
        this.formLayout = formLayout;
        this.entityPanel = entityPanel;
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        setModal(true);
        content.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        content.addComponent(formLayout);
        binder = new BeanValidationBinder<>(type);
        binder.bindInstanceFields(formLayout);
        okButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        okButton.addClickListener(e -> save());
        cancelButton.addClickListener(e -> cancel());
        HorizontalLayout buttonPanel = new HorizontalLayout(okButton, cancelButton);
        content.addComponent(buttonPanel);
    }

    public Binder getBinder() {
        return binder;
    }


    public void setEntity(E entity) {
        this.entity = entity;
        binder.setBean(entity);
    }

    private void cancel() {
        this.close();
    }

    private void save() {
        try {
            binder.writeBean(entity);
            entityPanel.updateGrid(entity);
            this.close();
        } catch (ValidationException ex) {

        } catch (Exception e) {
            Notification.show("Ошибка", e.getMessage(),
                    Notification.Type.ERROR_MESSAGE);
        }
    }
}
