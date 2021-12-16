package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.CompanyService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ContactPersonService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.StudentService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.ProjectDetails;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student.EditProjectDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.MainView;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.dozent.DozentProjectOverview;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Route(value = "projektdetails_student/:projectid", layout = MainView.class)
@PageTitle("Projektverwaltung | Projektdetails")
@CssImport("/themes/projektverwaltung/views/student/student-project-details.css")
public class StudentProjectDetails extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private final ProjectService projectService;
    private final StudentService studentService;
    private final ContactPersonService contactPersonService;
    private final CompanyService companyService;

    private Project project;
    private Long projectId;

    public StudentProjectDetails(ProjectService projectService, StudentService studentService, ContactPersonService contactPersonService, CompanyService companyService){
        this.projectService = projectService;
        this.studentService = studentService;
        this.contactPersonService = contactPersonService;
        this.companyService = companyService;
    }

    private void createStudentProjectDetails(){

        ProjectDetails projectDetails = new ProjectDetails(project, projectService, contactPersonService, companyService);

        HorizontalLayout buttonBox = new HorizontalLayout();

        Button buttonEdit = new Button("Projekt bearbeiten");
        buttonEdit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonEdit.setClassName("student-project-details-buttons");
        if(project.getStatus().equals(Status.ZUGELASSEN) || project.getStatus().equals(Status.ANFRAGE)){
            buttonEdit.setEnabled(false);
        }
        buttonEdit.addClickListener(editProjectEvent -> {
            EditProjectDialog editProject = new EditProjectDialog(projectService, project);
            editProject.open();
        });

        Button buttonCancel = new Button("Schließen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("student-project-details-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> UI.getCurrent().navigate(StudentProjectOverview.class));

        buttonBox.add(buttonEdit, buttonCancel);
        buttonBox.addClassName("student-project-details-buttonbox");

        Div div = new Div(projectDetails, buttonBox);
        div.setClassName("student-project-details");
        add(div);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getRouteParameters().get("projectid").isPresent()){
            String id = beforeEnterEvent.getRouteParameters().get("projectid").get();
            projectId = Long.parseLong(id);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        //TODO: Man kann über die Url sich einfach jedes Projekt ansehen unabhängig vom Login und ich kann auch 500 suchen z.b.
        //TODO: Ich kann genauso gut auf die DozentenUrl zugreifen und wenn man dann schließt fliegt eine Exception

        Optional<Project> projectOptional = projectService.findProjectById(projectId);

        projectOptional.ifPresent(value -> project = value);

        createStudentProjectDetails();
    }

}
