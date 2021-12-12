package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.dozent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ModuleCoordinatorService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent.AddAppointments;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent.AddFreeProjects;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student.NewProjectRequestDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.MainView;

/**
 * Die Klasse DozentProjectOverview ist die Startseite für Dozenten und zeigt
 * eine Übersicht aller Projekte des Dozenten. Er kann dabei nach laufenden Projekten,
 * Projektanfragen oder noch freien Projektplätzen filtern. Außerdem kann er Anfragen
 * bearbeiten, neue Projektplätze freigeben oder Termine zu Projekten vergeben.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@Route(value = "projektuebersicht_dozent", layout = MainView.class)
@PageTitle("Projektverwaltung | Projektübersicht")
@CssImport("/themes/projektverwaltung/views/dozent/dozent-project-overview.css")
public class DozentProjectOverview extends VerticalLayout {

    private final ModuleCoordinatorService moduleCoordinatorService;
    private final ProjectService projectService;

    public DozentProjectOverview(ModuleCoordinatorService moduleCoordinatorService, ProjectService projectService){

        this.moduleCoordinatorService = moduleCoordinatorService;
        this.projectService = projectService;

        H1 title = new H1("Projektverwaltung");
        title.setId("title-projektverwaltung-dozent");

        Select<String> selectModule = new Select<>();
        selectModule.setItems("Software Engineering");
        selectModule.setPlaceholder("Modul");
        selectModule.setClassName("dozent-project-overview-select");

        RadioButtonGroup<String> projectOption = new RadioButtonGroup<>();
        projectOption.setItems("laufende Projekte", "Projektanfragen", "Projektplätze");
        projectOption.setClassName("dozent-project-overview-radio");

        Grid<String> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setClassName("dozent-project-overview-grid");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        Button buttonNewAppointment = new Button("Termin vergeben");
        buttonNewAppointment.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNewAppointment.setClassName("dozent-projekt-overview-button");
        buttonNewAppointment.addClickListener(newAppointmentEvent -> {
            AddAppointments dialog = new AddAppointments(this.projectService);
            dialog.open();
        });

        Button buttonUpdateProjectRequest = new Button("Projekt bearbeiten");
        buttonUpdateProjectRequest.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonUpdateProjectRequest.setClassName("dozent-projekt-overview-button");

        Button buttonNewProjects = new Button("Projekte vergeben");
        buttonNewProjects.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNewProjects.setClassName("dozent-projekt-overview-button");
        buttonNewProjects.addClickListener(newProjectEvent -> {
            AddFreeProjects dialog = new AddFreeProjects(this.moduleCoordinatorService, this.projectService);
            dialog.open();
        });

        HorizontalLayout buttonBox = new HorizontalLayout();
        buttonBox.setClassName("dozent-projekt-overview-buttonbox");
        buttonBox.add(buttonNewAppointment, buttonUpdateProjectRequest, buttonNewProjects);

        Div div = new Div(title, selectModule, projectOption, grid, buttonBox);
        div.setClassName("dozent-project-overview");
        add(div);
    }
}
