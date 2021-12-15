package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.dozent;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ContactPersonService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.StudentService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.NotificationPrimary;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.ProjectDetails;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent.ProjectModificationDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.MainView;
import java.util.Optional;

@Route(value = "projektdetails_dozent/:projectid", layout = MainView.class)
@PageTitle("Projektverwaltung | Projektdetails")
@CssImport("/themes/projektverwaltung/views/dozent/dozent-project-details.css")
public class DozentProjectDetails extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private final ProjectService projectService;
    private final StudentService studentService;
    private final ContactPersonService contactPersonService;
    private Project project;
    private Long projectId;

    public DozentProjectDetails(ProjectService projectService, StudentService studentService, ContactPersonService contactPersonService){

        this.projectService = projectService;
        this.studentService = studentService;
        this.contactPersonService = contactPersonService;
    }

    private void createDozentProjectDetails(){

        ProjectDetails projectDetails = new ProjectDetails(project);

        HorizontalLayout buttonBox = new HorizontalLayout();
        buttonBox.addClassName("dozent-project-details-buttonbox");

        Button buttonAccept = new Button("Annehmen");
        buttonAccept.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonAccept.setClassName("dozent-project-details-buttons");
        buttonAccept.addClickListener(acceptProjectEvent -> {
            project.setStatus(Status.ZUGELASSEN);
            projectService.saveProject(project);
            UI.getCurrent().navigate(DozentProjectOverview.class);
            NotificationPrimary notificationAccept = NotificationPrimary.show("Das Projekt wurde angenommen");
        });

        Button buttonModification = new Button("Ergänzung fordern");
        buttonModification.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonModification.setClassName("dozent-project-details-buttons");
        buttonModification.addClickListener(modificateProjectEvent -> {
            project.setStatus(Status.ERGAENZUNG);
            ProjectModificationDialog projectModificationDialog = new ProjectModificationDialog(projectService, project);
            projectModificationDialog.open();
        });

        Button  buttonDecline= new Button("Ablehnen");
        buttonDecline.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonDecline.setClassName("dozent-project-details-buttons");
        buttonDecline.addClickListener(declineProjectEvent -> {
            project.setStatus(Status.ABGELEHNT);
            projectService.saveProject(project);
            UI.getCurrent().navigate(DozentProjectOverview.class);
            NotificationPrimary notificationDecline = NotificationPrimary.show("Das Projekt wurde abgelehnt");
        });
        
        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("dozent-project-details-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> UI.getCurrent().navigate(DozentProjectOverview.class));


        if(project.getStatus().equals(Status.ANFRAGE)){
            buttonBox.add(buttonAccept, buttonModification, buttonDecline, buttonCancel);
            buttonBox.setClassName("dozent-project-details-buttonbox1");
        }
        else{
            buttonBox.add(buttonCancel);
            buttonCancel.setText("Schließen");
            buttonBox.setClassName("dozent-project-details-buttonbox2");
        }

        Div div = new Div(projectDetails, buttonBox);
        div.setClassName("dozent-project-details");
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
        Optional<Project> projectOptional = projectService.findProjectById(projectId);
        projectOptional.ifPresent(value -> project = value);
        createDozentProjectDetails();
    }
}
