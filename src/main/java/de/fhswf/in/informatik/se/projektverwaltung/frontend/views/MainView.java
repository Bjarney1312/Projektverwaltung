package de.fhswf.in.informatik.se.projektverwaltung.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.ButtonSwitchTheme;

@PWA(name = "Projektverwaltung", shortName = "PV", enableInstallPrompt = false)
//@Theme(themeFolder = "myapp")
//@CssImport("/themes/myapp/views/main-view.css")
//@CssImport(value = "/themes/myapp/components/menu-bar-button.css", themeFor = "vaadin-menu-bar-button")
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

//        StreamResource streamResource = new StreamResource("logoFH.jpg",
//                () -> MainView.class.getClassLoader().
//                        getResourceAsStream("images/logoFH.jpg"));

//        Image logoFH = new Image (streamResource, "FH SWF");
//        logoFH.setHeight("50px");

        Label title = new Label("Projektverwaltung");
        ButtonSwitchTheme switchTheme = new ButtonSwitchTheme();

        Anchor logout = new Anchor("logout", "Logout");
//
//        RouterLink linkHome = new RouterLink("Home", NotificationView.class);
//        RouterLink linkTwo = new RouterLink("Something", TestView.class); // überarbeiten


        Tabs tabs = new Tabs (new Tab(logout));

        HorizontalLayout header = new HorizontalLayout(title, tabs, switchTheme);
        header.addClassName("header");
        header.setWidth("100%");

        addToNavbar(header);
    }

}
