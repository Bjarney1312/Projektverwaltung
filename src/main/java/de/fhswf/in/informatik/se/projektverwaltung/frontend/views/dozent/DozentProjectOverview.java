package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.dozent;

import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.router.RouteParameters;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ModuleCoordinator;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ModuleCoordinatorService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent.AddAppointmentsDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent.AddFreeProjectsDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.MainView;

import java.util.ArrayList;
import java.util.List;

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
    private Long projectId;

    public DozentProjectOverview(ModuleCoordinatorService moduleCoordinatorService, ProjectService projectService) {

        this.moduleCoordinatorService = moduleCoordinatorService;
        this.projectService = projectService;

        ModuleCoordinator moduleCoordinator = moduleCoordinatorService.findByUsername();

        H1 title = new H1("Projektverwaltung");
        title.setId("title-projektverwaltung-dozent");

        Select<String> selectModule = new Select<>();
        selectModule.setPlaceholder("Modul");
        selectModule.setClassName("dozent-project-overview-select");
        List<String> moduleList = new ArrayList<>();
        for (ModuleEnum module : moduleCoordinatorService.findByUsername().getModul()) {
            moduleList.add(module.label);
        }
        moduleList.add("Alle Module");
        selectModule.setItems(moduleList);

        RadioButtonGroup<String> projectOption = new RadioButtonGroup<>();
        projectOption.setItems("laufende Projekte", "Projektanfragen");
        projectOption.setClassName("dozent-project-overview-radio");

        Grid<Project> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setClassName("dozent-project-overview-grid");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addColumn(projectTitle -> projectTitle.getProjectDescription().getTitle()).setHeader("Projekttitel");

        grid.addColumn(Project::getModule).setHeader("Modul");
        grid.addColumn(Project::getStatus).setHeader("Projektstatus");
        grid.addColumn(mc ->
                mc.getModuleCoordinator().getLastName()
                        + ", " + mc.getModuleCoordinator().getFirstName()).setHeader("Modulbeauftragter");

        grid.addColumn(contactPerson ->
                contactPerson.getContactPerson().getLastName()
                        + ", " + contactPerson.getContactPerson().getFirstName()).setHeader("Ansprechpartner");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.setItems(projectService.getAllProjectsWithoutEmpty(moduleCoordinator));
        grid.getColumns().get(0).setFooter("Freie Projektplätze: " + projectService.getAllProjectsByStatus(moduleCoordinator, Status.FREI).size());


        selectModule.addValueChangeListener(e -> {
            if (e.getValue().equals("Alle Module")) {
                grid.setItems(projectService.getAllProjectsWithoutEmpty(moduleCoordinator));
                grid.getColumns().get(0).setFooter("Freie Projektplätze: " + projectService.getAllProjectsByStatus(moduleCoordinator, Status.FREI).size());
            } else {
                grid.setItems(projectService.getAllByModuleAndStatusNot(e.getValue()));
                grid.getColumns().get(0).setFooter("Freie Projektplätze: " + projectService.getAllByStatusAndModule(Status.FREI, e.getValue()).size());
            }
            projectOption.setValue(null);
        });

        projectOption.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                return;
            }

            if (selectModule.getValue() == null || selectModule.getValue().equals("Alle Module")) {
                if (e.getValue().equals("laufende Projekte")) {
                    grid.setItems(projectService.getAllProjectsByStatus(moduleCoordinator, Status.ZUGELASSEN));
                } else {
                    List<Project> test = new ArrayList<>();
                    test.addAll(projectService.getAllProjectsByStatus(moduleCoordinator, Status.ANFRAGE));
                    test.addAll(projectService.getAllProjectsByStatus(moduleCoordinator, Status.ERGAENZUNG));
                    grid.setItems(test);
                }
            } else {
                if (e.getValue().equals("laufende Projekte")) {
                    grid.setItems(projectService.getAllByStatusAndModule(Status.ZUGELASSEN, selectModule.getValue()));
                } else {
                    List<Project> test = new ArrayList<>();
                    test.addAll(projectService.getAllByStatusAndModule(Status.ANFRAGE, selectModule.getValue()));
                    test.addAll(projectService.getAllByStatusAndModule(Status.ERGAENZUNG, selectModule.getValue()));
                    grid.setItems(test);
                }
            }
        });


        Button buttonNewAppointment = new Button("Termin vergeben");
        buttonNewAppointment.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNewAppointment.setClassName("dozent-projekt-overview-button");
        buttonNewAppointment.setEnabled(false);
        buttonNewAppointment.addClickListener(newAppointmentEvent -> {
            AddAppointmentsDialog dialog = new AddAppointmentsDialog(this.projectService, projectId);
            dialog.open();
        });

        Button buttonProjectDetails = new Button("Projekt ansehen");
        buttonProjectDetails.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonProjectDetails.setClassName("dozent-projekt-overview-button");
        buttonProjectDetails.setEnabled(false);
        buttonProjectDetails.addClickListener(updateProjectEvent -> {
            UI.getCurrent().navigate(DozentProjectDetails.class, new RouteParameters("projectid", projectId.toString()));
        });

        Button buttonNewProjects = new Button("Projekte vergeben");
        buttonNewProjects.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNewProjects.setClassName("dozent-projekt-overview-button");
        buttonNewProjects.addClickListener(newProjectEvent -> {
            AddFreeProjectsDialog dialog = new AddFreeProjectsDialog(this.moduleCoordinatorService, this.projectService);
            dialog.open();
        });

        grid.addSelectionListener(event -> {
            if(event.getAllSelectedItems().isEmpty()){
                buttonProjectDetails.setEnabled(false);
                buttonNewAppointment.setEnabled(false);
            }
            else{
                buttonProjectDetails.setEnabled(true);
                event.getFirstSelectedItem().ifPresent(project -> {
                    projectId = project.getId();
                    if(project.getStatus().equals(Status.ANFRAGE)){
                        buttonProjectDetails.setText("Projekt bearbeiten");
                    }
                    else{
                        buttonProjectDetails.setText("Projekt ansehen");
                    }

                    if(project.getStatus().equals(Status.ZUGELASSEN)){
                        buttonNewAppointment.setEnabled(true);
                    }
                    else{
                        buttonNewAppointment.setEnabled(false);
                    }
                });
            }
        });

        HorizontalLayout buttonBox = new HorizontalLayout();
        buttonBox.setClassName("dozent-projekt-overview-buttonbox");
        buttonBox.add(buttonNewAppointment, buttonProjectDetails, buttonNewProjects);

        Div div = new Div(title, selectModule, projectOption, grid, buttonBox);
        div.setClassName("dozent-project-overview");
        add(div);
    }
}
