package com.mycompany.clinic.view;

import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;

public class Menu extends HorizontalLayout {
    public Menu() {
        super();
        setMargin(true);
        Link link1 = new Link("Пациенты", new ExternalResource("patients"));
        Link link2 = new Link("Врачи", new ExternalResource("doctors"));
        Link link3 = new Link("Рецепты", new ExternalResource("/"));
        addComponents(link1, link2, link3);
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setMargin(new MarginInfo(false, true));
    }
}
