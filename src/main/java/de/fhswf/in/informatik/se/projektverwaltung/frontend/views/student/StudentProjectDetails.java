package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ContactPerson;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ContactPersonService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.StudentService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.MainView;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Route(value = "projektdetails_student/:projectid", layout = MainView.class)
@PageTitle("Projektverwaltung | Projektdetails")
@CssImport("/themes/projektverwaltung/views/student/student-project-details.css")
public class StudentProjectDetails extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private final ProjectService projectService;
    private final StudentService studentService;
    private final ContactPersonService contactPersonService;
    private Project project;
    private Long projectId;

    public StudentProjectDetails(ProjectService projectService, StudentService studentService, ContactPersonService contactPersonService){

        this.projectService = projectService;
        this.studentService = studentService;
        this.contactPersonService = contactPersonService;
    }

    private void createStudentProjectDetails(){
        H1 title = new H1("Projektdetails");
        title.setId("student-project-details-title");

        //Modul
        H2 module = new H2("Modul");
        Label moduleName = new Label("Modul: " + project.getModuleEnum());
        moduleName.setClassName("student-project-details-h2");

        //Ansprechpartner
//        Select<String> contactName = new Select<>();
//        contactName.setItems(contactPersonService.getAllContactPersonNames());
//        contactName.setLabel("Ansprechpartner");
//        contactName.setEnabled(false);
//        contactName.setValue(project.getContactPerson().getLastName() + ", " + project.getContactPerson().getFirstName());
        H2 contact = new H2("Ansprechpartner");
        contact.setClassName("student-project-details-h2");
        Label contactName = new Label(project.getContactPerson().getLastName()
                + ", " + project.getContactPerson().getFirstName());

        Label contactMail = new Label(project.getContactPerson().getEmail());

        //Unternehmen
        H2 company = new H2("Unternehmen");
        Label companyName = new Label(project.getContactPerson().getCompany().getCompanyName());
        Label companyStreet = new Label(project.getContactPerson().getCompany().getStreet() + " "
                + project.getContactPerson().getCompany().getHouseNumber());
        Label companyPlace = new Label(project.getContactPerson().getCompany().getPostalCode() + " "
                + project.getContactPerson().getCompany().getLocation());

        //Gruppenmitglieder
        H2 group = new H2("Gruppenmitglieder");
        Student[] students = project.getStudents().toArray(new Student[0]);
        Label groupMemberOne = new Label (students[0].getFirstName() + students[0].getLastName());
        Label groupMemberTwo = new Label(students[1].getFirstName() + students[1].getLastName());
        Label groupMemberThree = new Label();
        if(students.length == 3){
            groupMemberThree.setText(students[2].getFirstName() + students[2].getLastName());
        }

        //Termine
        H2 appointments = new H2("Termine");
        Label appointmentOne = new Label("get Termin einfügen!");
        Label appointmentTwo = new Label("get Termin einfügen!");




        //Projekt
        H2 projectTitle = new H2(project.getProjectDescription().getTitle());

        H3 sketchTitle = new H3("Projektskizze");
        Label projectSketch = new Label(project.getProjectDescription().getSketch());

        H3 backgroundTitle = new H3("Projekthintergrund");
        Label projectBackground = new Label(project.getProjectDescription().getDescriptionBackground());

        H3 descriptopnTitle = new H3("Projektbeschreibung");
        byte[] pdfByte = project.getProjectDescription().getDescriptionProjectContent();
        StreamResource res = new StreamResource("Projektbeschreibung.pdf", () -> new ByteArrayInputStream(pdfByte));

        Anchor projectDescription = new Anchor("Download");
        projectDescription.setHref(res);
        projectDescription.setText("Download");
        projectDescription.setTarget(AnchorTarget.BLANK);

        HorizontalLayout buttonBox = new HorizontalLayout();

        Button buttonEdit = new Button("Projekt bearbeiten");
        buttonEdit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonEdit.setClassName("student-project-details-buttons");
        buttonEdit.setEnabled(false);
//        buttonEdit.addClickListener(newProjectEvent -> {});

        Button buttonCancel = new Button("Schließen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("student-project-details-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> UI.getCurrent().navigate(StudentProjectOverview.class));

        buttonBox.add(buttonEdit, buttonCancel);
        buttonBox.addClassName("student-project-details-buttonbox");

        FormLayout projectDetailsTop = new FormLayout(
                module, contact,
                moduleName, contactName,
                new Label(), contactMail,
                group, company,
                groupMemberOne, companyName,
                groupMemberTwo, companyStreet,
                groupMemberThree, companyPlace,
                appointments, new Label(),
                appointmentOne, appointmentTwo
        );
        projectDetailsTop.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        projectDetailsTop.setClassName("student-project-details-upperformlayout");


        FormLayout projectDetailsBottom = new FormLayout(
                projectTitle,
                sketchTitle,
                projectSketch,
                backgroundTitle,
                projectBackground,
                descriptopnTitle,
                projectDescription
        );
        projectDetailsBottom.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        projectDetailsBottom.setClassName("student-project-details-lowerformlayout");

        Div div = new Div(title, projectDetailsTop, projectDetailsBottom, buttonBox);
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
        Optional<Project> projectOptional = projectService.findProjectById(projectId);
        projectOptional.ifPresent(value -> project = value);
        createStudentProjectDetails();
    }

}
