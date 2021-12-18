package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.dozent;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
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
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent.DeleteFreeProjectsDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent.GroupMemberLayout;
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
        projectOption.setItems("laufende Projekte", "Projektanfragen", "Alle Projekte");
        projectOption.setClassName("dozent-project-overview-radio");

        Grid<Project> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setClassName("dozent-project-overview-grid");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addColumn(projectTitle -> projectTitle.getProjectDescription().getTitle())
                .setHeader("Projekttitel")
                .setResizable(true)
                .setSortable(true);

        grid.addColumn(Project::getModule).setHeader("Modul").setSortable(true);
        grid.addColumn(Project::getStatus).setHeader("Projektstatus").setSortable(true);

        grid.addColumn(contactPerson ->
                contactPerson.getContactPerson().getLastName()
                        + ", " + contactPerson.getContactPerson().getFirstName()).setHeader("Ansprechpartner").setSortable(true);

        grid.addColumn(createToggleDetailsRenderer(grid));

        grid.getColumns().get(0).setWidth("200px");
        grid.getColumns().get(1).setWidth("200px");
        grid.getColumns().get(2).setWidth("150px");
        grid.getColumns().get(3).setWidth("180px");
        grid.getColumns().get(4).setWidth("250px");

        grid.setItems(projectService.getAllProjectsWithoutEmpty(moduleCoordinator));


        HorizontalLayout gridFooterLayout = new HorizontalLayout();

        Label freeProjectsLabel = new Label();
        freeProjectsLabel.setText("Freie Projektplätze: " + projectService.getAllProjectsByStatus(moduleCoordinator, Status.FREI).size());
        freeProjectsLabel.setClassName("dozent-project-overview-freeprojects");

        Button deleteFreeProjectButton = new Button();
        deleteFreeProjectButton.setIcon(new Icon(VaadinIcon.TRASH));
        deleteFreeProjectButton.setVisible(false);
        deleteFreeProjectButton.setClassName("dozent-project-overview-trash");
        deleteFreeProjectButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        deleteFreeProjectButton.addClickListener(e -> {
            DeleteFreeProjectsDialog deleteProjects = new DeleteFreeProjectsDialog(projectService, selectModule.getValue());
            deleteProjects.open();
        });

        gridFooterLayout.add(deleteFreeProjectButton, freeProjectsLabel);

        grid.getColumns().get(0).setFooter(gridFooterLayout);

        //Für das aufklappen im Grid
        grid.setItemDetailsRenderer(createGroupMemberDetails());
        grid.setDetailsVisibleOnClick(false);


        projectOption.setValue("Alle Projekte");

        selectModule.addValueChangeListener(e -> {
            if (e.getValue().equals("Alle Module")) {
                grid.setItems(projectService.getAllProjectsWithoutEmpty(moduleCoordinator));
                freeProjectsLabel.setText("Freie Projektplätze: " + projectService.getAllProjectsByStatus(moduleCoordinator, Status.FREI).size());
                deleteFreeProjectButton.setVisible(false);
            } else {
                grid.setItems(projectService.getAllByModuleAndStatusNot(e.getValue()));
                freeProjectsLabel.setText("Freie Projektplätze: " + projectService.getAllByStatusAndModule(Status.FREI, e.getValue()).size());
                deleteFreeProjectButton.setVisible(projectService.getAllByStatusAndModule(Status.FREI, e.getValue()).size() != 0);
            }
            projectOption.setValue("Alle Projekte");
        });

        projectOption.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                return;
            }

            if (selectModule.getValue() == null || selectModule.getValue().equals("Alle Module")) {
                switch (e.getValue()) {
                    case "laufende Projekte" -> grid.setItems(projectService.getAllProjectsByStatus(moduleCoordinator, Status.ZUGELASSEN));
                    case "Projektanfragen" -> {
                        List<Project> test = new ArrayList<>();
                        test.addAll(projectService.getAllProjectsByStatus(moduleCoordinator, Status.ANFRAGE));
                        test.addAll(projectService.getAllProjectsByStatus(moduleCoordinator, Status.ERGAENZUNG));
                        grid.setItems(test);
                    }
                    case "Alle Projekte" -> grid.setItems(projectService.getAllProjectsWithoutEmpty(moduleCoordinator));
                }
            } else {
                switch (e.getValue()) {
                    case "laufende Projekte" -> grid.setItems(projectService.getAllByStatusAndModule(Status.ZUGELASSEN, selectModule.getValue()));
                    case "Projektanfragen" -> {
                        List<Project> projectList = new ArrayList<>();
                        projectList.addAll(projectService.getAllByStatusAndModule(Status.ANFRAGE, selectModule.getValue()));
                        projectList.addAll(projectService.getAllByStatusAndModule(Status.ERGAENZUNG, selectModule.getValue()));
                        grid.setItems(projectList);
                    }
                    case "Alle Projekte" -> {
                        List<Project> projectListAll = new ArrayList<>();
                        projectListAll.addAll(projectService.getAllByStatusAndModule(Status.ZUGELASSEN, selectModule.getValue()));
                        projectListAll.addAll(projectService.getAllByStatusAndModule(Status.ANFRAGE, selectModule.getValue()));
                        projectListAll.addAll(projectService.getAllByStatusAndModule(Status.ERGAENZUNG, selectModule.getValue()));
                        grid.setItems(projectListAll);
                    }
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
        buttonProjectDetails.addClickListener(updateProjectEvent -> UI.getCurrent().navigate(DozentProjectDetails.class,
                new RouteParameters("projectid", projectId.toString())));

        Button buttonNewProjects = new Button("Projekte vergeben");
        buttonNewProjects.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNewProjects.setClassName("dozent-projekt-overview-button");
        buttonNewProjects.addClickListener(newProjectEvent -> {
            AddFreeProjectsDialog dialog = new AddFreeProjectsDialog(this.moduleCoordinatorService, this.projectService);
            dialog.open();
        });

        grid.addSelectionListener(event -> {
            if (event.getAllSelectedItems().isEmpty()) {
                buttonProjectDetails.setEnabled(false);
                buttonNewAppointment.setEnabled(false);
            } else {
                buttonProjectDetails.setEnabled(true);
                event.getFirstSelectedItem().ifPresent(project -> {
                    projectId = project.getId();
                    if (project.getStatus().equals(Status.ANFRAGE)) {
                        buttonProjectDetails.setText("Projekt bearbeiten");
                    } else {
                        buttonProjectDetails.setText("Projekt ansehen");
                    }
                    buttonNewAppointment.setEnabled(project.getStatus().equals(Status.ZUGELASSEN));
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


    private static TemplateRenderer<Project> createToggleDetailsRenderer(
            Grid<Project> grid) {
        return TemplateRenderer.<Project>of(
                        "<vaadin-button theme=\"tertiary\" on-click=\"handleClick\"> Gruppenmitglieder anzeigen </vaadin-button>")
                .withEventHandler("handleClick", project -> grid
                        .setDetailsVisible(project,
                                !grid.isDetailsVisible(project)));
    }


    private static ComponentRenderer<GroupMemberLayout, Project> createGroupMemberDetails() {
        return new ComponentRenderer<>(
                GroupMemberLayout::new,
                GroupMemberLayout::setGroupMember);
    }
}

