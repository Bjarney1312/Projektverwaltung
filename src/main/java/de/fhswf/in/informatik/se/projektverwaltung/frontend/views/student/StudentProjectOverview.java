package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.student;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.StudentService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student.NewProjectRequestDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.MainView;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse StudentProjectOverview ist die Startseite für Studenten
 * und zeigt eine Übersicht seiner aktiven Projekte. Außerdem kann er
 * einen neuen Projektantrag stellen oder sich die Details eines
 * bestehenden Projekts anzeigen lassen.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@Route(value = "projektuebersicht_student", layout = MainView.class)
@PageTitle("Projektverwaltung | Projektübersicht")
@CssImport("/themes/projektverwaltung/views/student/student-project-overview.css")
public class StudentProjectOverview extends VerticalLayout {

    private final ProjectService projectService;

    private final StudentService studentService;

    private Long projectId;

    public StudentProjectOverview(ProjectService projectService, StudentService studentService){

        this.projectService = projectService;
        this.studentService = studentService;

        H1 title = new H1("Projektverwaltung");
        title.setId("title-projektverwaltung-student");

        Grid<Project> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.setClassName("student-project-overview-grid");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentService.getStudentByUsername(username);

        grid.setItems(this.projectService.getAllProjectsByStudents(student));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addColumn(projectTitle -> projectTitle.getProjectDescription().getTitle()).setHeader("Projekttitel");

        grid.addColumn(Project::getModule).setHeader("Modul");
        grid.addColumn(Project::getStatus).setHeader("Projektstatus");
        grid.addColumn(moduleCoordinator ->
                moduleCoordinator.getModuleCoordinator().getLastName()
                        + ", " + moduleCoordinator.getModuleCoordinator().getFirstName()).setHeader("Modulbeauftragter");
        grid.addColumn(contactPerson ->
                contactPerson.getContactPerson().getLastName()
                        + ", " + contactPerson.getContactPerson().getFirstName()).setHeader("Ansprechpartner");


        grid.setSelectionMode(Grid.SelectionMode.SINGLE);


        Button buttonNewProject = new Button("Projektvorschlag");
        buttonNewProject.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNewProject.setClassName("student-projekt-overview-button");
        buttonNewProject.addClickListener(newProjectEvent -> {
            List<String> studentModules = new ArrayList<>();
            for(Project projects : this.projectService.getAllProjectsByStudents(student)){
                studentModules.add(projects.getModule());
            }
            NewProjectRequestDialog dialog = new NewProjectRequestDialog(projectService, studentModules);
            dialog.open();
        });

        Button buttonProjectDetails = new Button("Projekt ansehen");
        buttonProjectDetails.setEnabled(false);
        buttonProjectDetails.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonProjectDetails.setClassName("student-projekt-overview-button");
        buttonProjectDetails.addClickListener(projectDetailsEvent -> {

            //TODO: Variante 2
//            if(grid.getSelectedItems().isEmpty()){
//                Notification notification = Notification.show(
//                        "Es wurde keine Zeile ausgewählt!",
//                        5000,
//                        Notification.Position.BOTTOM_START
//                );
//                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//                return;
//            }
            List<Project> projectList= new ArrayList<>(grid.getSelectedItems());
            UI.getCurrent().navigate(StudentProjectDetails.class, new RouteParameters("projectid", projectId.toString()));
        });

        //TODO: Variante 1
        grid.addSelectionListener(event -> {
            if(event.getAllSelectedItems().isEmpty()){
                buttonProjectDetails.setEnabled(false);
            }
            else{
                buttonProjectDetails.setEnabled(true);
                event.getFirstSelectedItem().ifPresent(project -> projectId = project.getId());
            }
        });

        HorizontalLayout buttonBox = new HorizontalLayout();
        buttonBox.setClassName("student-projekt-overview-buttonbox");
        buttonBox.add(buttonNewProject, buttonProjectDetails);

        Div div = new Div(title,grid, buttonBox);
        div.setClassName("student-project-overview");
        add(div);
    }
}
