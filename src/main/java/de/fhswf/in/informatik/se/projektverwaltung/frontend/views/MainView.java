package de.fhswf.in.informatik.se.projektverwaltung.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.PWA;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ModuleCoordinator;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ModuleCoordinatorService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.StudentService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.ButtonSwitchTheme;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Die Klasse LoginView erstellt die Menüleiste der Projektverwaltungs-Applikation
 *
 * @author Ivonne Kneißig & Ramon Günther
 */

@PWA(name = "Projektverwaltung", shortName = "PV", enableInstallPrompt = false)
@CssImport("/themes/projektverwaltung/main-layout-title.css")
public class MainView extends AppLayout {

    private final StudentService studentService;
    private final ModuleCoordinatorService moduleCoordinatorService;
    private String name;

    /**
     * Die Konstruktor erstellt eine Menüleiste für die Applikation.
     */
    public MainView(StudentService studentService, ModuleCoordinatorService moduleCoordinatorService){

        this.studentService = studentService;
        this.moduleCoordinatorService = moduleCoordinatorService;

        createMenuBar();
    }

    /**
     * Die Methode createMenuBar() baut die Menüleiste der Applikation
     * zusammen. Sie besteht aus einem Titel, einer Begrüßung, dem Logout-
     * Button und der Möglichkeit in einen Darkmode zu wechseln.
     */

    private void createMenuBar(){

        Icon academyCap = new Icon(VaadinIcon.ACADEMY_CAP);
        academyCap.setClassName("academycap");

        H1 title = new H1("Projektverwaltung");
        title.addClassName("main-layout-title");

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().contains("STUDENT")){
            Student student = studentService.getStudentByUsername();
            name = student.getFirstName() + " " + student.getLastName() + " !";
        }
        else if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().contains("DOZENT")) {
            ModuleCoordinator moduleCoordinator = moduleCoordinatorService.findByUsername();
            name = moduleCoordinator.getFirstName() + " " + moduleCoordinator.getLastName() + " !";
        }

        Label user = new Label("Willkommen " + name);
        user.addClassName("user");

        HorizontalLayout welcome = new HorizontalLayout(academyCap, title, user);
        welcome.setClassName("welcome");

        ButtonSwitchTheme switchTheme = new ButtonSwitchTheme();
        switchTheme.addClassName("switch-theme");

        Icon door = new Icon(VaadinIcon.SIGN_OUT);
        Button btnLogout = new Button("Abmelden");
        btnLogout.setIcon(door);
        btnLogout.setClassName("btnlogout");
        btnLogout.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        btnLogout.addClickListener(e -> UI.getCurrent().getPage().setLocation("/logout"));

        HorizontalLayout buttonBox = new HorizontalLayout(switchTheme, btnLogout);
        buttonBox.setClassName("btnBox");

        HorizontalLayout header = new HorizontalLayout(welcome, buttonBox);
        header.addClassName("header");
        header.setWidth("100%");

        addToNavbar(header);
    }
}
