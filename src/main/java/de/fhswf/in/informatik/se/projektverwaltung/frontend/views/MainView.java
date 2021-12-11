package de.fhswf.in.informatik.se.projektverwaltung.frontend.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.PWA;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.ButtonSwitchTheme;

@PWA(name = "Projektverwaltung", shortName = "PV", enableInstallPrompt = false)
public class MainView extends AppLayout {

    /**
     * Die Konstruktor erstellt eine Menüleiste für die Applikation.
     */

    public MainView(){

        createMenuBar();

    }

    /**
     * Die Methode createMenuBar() baut die Menüleiste der Applikation
     * zusammen. Außerdem weißt sie den RouterLinks die entsprechenden
     * Views zur Navigation zu.
     */

    private void createMenuBar(){

        Label title = new Label("Projektverwaltung");
        ButtonSwitchTheme switchTheme = new ButtonSwitchTheme();

        Anchor logout = new Anchor("logout", "Logout");

        Tabs tabs = new Tabs (new Tab(logout));

        HorizontalLayout header = new HorizontalLayout(title, tabs, switchTheme);
        header.addClassName("header");
        header.setWidth("100%");

        addToNavbar(header);
    }

}
