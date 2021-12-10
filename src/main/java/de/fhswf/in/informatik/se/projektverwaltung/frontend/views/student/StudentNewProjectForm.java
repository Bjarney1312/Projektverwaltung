package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.*;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.valueobjects.ProjectDescription;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.*;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student.NewContactDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.MainView;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Die Klasse StudentNewProjectForm zeigt eine View für den Studenten, um einen
 * neuen Projektantrag zu stellen. Hier muss der Student alle notwendigen
 * Angaben zu einem Projekt machen, um den Projektantrag für den Dozenten des
 * gewählten Moduls abzusenden.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@Route(value = "projektantrag_student/:projectid", layout = MainView.class)
@PageTitle("Projektverwaltung | Projektantrag")
@CssImport("/themes/projektverwaltung/views/student/student-new-project-form.css")
public class StudentNewProjectForm extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private Long projectId;
    private final Label moduleText;
    private byte[] pdfByte;
    private final Anchor uploadPreview;

    private Project project;
    private ProjectDescription projectDescription;
    private ContactPerson contactPerson;
    private Company company;

    private final ProjectService projectService;
    private final CompanyService companyService;
    private final ContactPersonService contactPersonService;
    private final StudentService studentService;

    private final TextField projectTitle;
    private final TextArea projectSketch;
    private final TextArea projectBackground;

    private final Student groupMemberOne;
    private Student groupMemberTwo;
    private Student groupMemberThree;

    public StudentNewProjectForm(ProjectService projectService,
                                 CompanyService companyService,
                                 ContactPersonService contactPersonService,
                                 StudentService studentService){

        this.projectService = projectService;
        this.companyService = companyService;
        this.contactPersonService = contactPersonService;
        this.studentService = studentService;


        /*-----------------------------------------------------------------------------------------------------------
                                               Oberer Teil / Projektdetails
        ------------------------------------------------------------------------------------------------------------*/

        H1 title = new H1("Projekt Details");

        moduleText = new Label("Modul: ");

        projectTitle = new TextField();
        projectTitle.setLabel("Projekttitel");
        projectTitle.addClassName("student-new-project-form-input");

        projectSketch = new TextArea();
        projectSketch.setLabel("Kurze Skizze");
        projectSketch.addClassName("student-new-project-form-input");

        projectBackground = new TextArea();
        projectBackground.setLabel("Beschreibung des Projekthintergrundes");
        projectBackground.addClassName("student-new-project-form-input");

        Label projectDescription = new Label("Beschreibung der wesentlichen Projektinhalte");
        projectDescription.setClassName("student-new-project-form-upload-label");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("application/pdf", ".pdf");
        upload.setMaxFiles(1);
        int maxFileSizeInBytes = 1024 * 1024 * 5;
        upload.setMaxFileSize(maxFileSizeInBytes);

        Button uploadButton = new Button("Upload PDF...");
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        uploadButton.setClassName("student-new-project-form-upload-button");
        upload.setUploadButton(uploadButton);
        upload.setClassName("student-new-project-form-upload");

        uploadPreview = new Anchor();
        uploadPreview.setClassName("student-new-project-form-uploadpreview");

        HorizontalLayout uploadArea = new HorizontalLayout(upload, uploadPreview);

        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(
                    errorMessage,
                    5000,
                    Notification.Position.BOTTOM_START
            );
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        upload.addFinishedListener(e -> {
            try {
                pdfByte = buffer.getInputStream().readAllBytes();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //"Vorschau"
            StreamResource res = new StreamResource("Projektbeschreibung.pdf", () -> new ByteArrayInputStream(pdfByte));
            uploadPreview.setHref(res);
            uploadPreview.setText("Vorschau");
            uploadPreview.setTarget(AnchorTarget.BLANK);
        });

        FormLayout projectDetailsTop = new FormLayout(projectTitle, projectSketch, projectBackground, projectDescription, uploadArea);
        projectDetailsTop.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        projectDetailsTop.setClassName("student-new-project-form-upperformlayout");


        /*-----------------------------------------------------------------------------------------------------------
                                         Unterer Teil / Gruppe und Ansprechpartner
         ------------------------------------------------------------------------------------------------------------*/

        H2 titleGroup = new H2("Gruppe");
        titleGroup.setClassName("test");
        H2 titleContact = new H2("Ansprechpartner");
        titleContact.setClassName("test");
        H2 titleCompany = new H2("Unternehmen");
        titleCompany.setClassName("test");

        TextField groupMemberOneFhMail = new TextField();
        groupMemberOneFhMail.setLabel("Fh-Mail Gruppenmitglied 1");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        groupMemberOne = studentService.getStudentByUsername(name);
        groupMemberOneFhMail.setValue(groupMemberOne.getFhMail());
        groupMemberOneFhMail.setReadOnly(true);

        TextField groupMemberOneName = new TextField();
        groupMemberOneName.setLabel("Name");
        groupMemberOneName.setReadOnly(true);
        groupMemberOneName.setValue(groupMemberOne.getFirstName() + " " + groupMemberOne.getLastName());

        TextField groupMemberTwoFhMail = new TextField();
        groupMemberTwoFhMail.setLabel("Name");
        groupMemberTwoFhMail.setReadOnly(true);

        TextField groupMemberTwoName = new TextField();
        groupMemberTwoName.setLabel("FH-Mail Gruppenmitglied 2");
        groupMemberTwoName.setPlaceholder("FH-Mail");
        groupMemberTwoName.addValueChangeListener(event -> {
            try {
                groupMemberTwo = studentService.getStudentByFhMail(groupMemberTwoName.getValue());
                groupMemberTwoFhMail.setValue(groupMemberTwo.getFirstName() + " " +groupMemberTwo.getLastName());
            }
            catch(Exception e){
                Notification notification = Notification.show(
                        e.getMessage(),
                        5000,
                        Notification.Position.BOTTOM_START
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });


        TextField groupMemberThreeFhMail = new TextField();
        groupMemberThreeFhMail.setLabel("Name");
        groupMemberThreeFhMail.setReadOnly(true);

        TextField groupMemberThreeName = new TextField();
        groupMemberThreeName.setLabel("FH-Mail Gruppenmitglied 3");
        groupMemberThreeName.setPlaceholder("FH-Mail");
        groupMemberThreeName.addValueChangeListener(event -> {
            try {
                groupMemberThree = studentService.getStudentByFhMail(groupMemberThreeName.getValue());
                groupMemberThreeFhMail.setValue(groupMemberThree.getFirstName() + " " + groupMemberThree.getLastName());
            }
            catch(Exception e){
                Notification notification = Notification.show(
                        e.getMessage(),
                        5000,
                        Notification.Position.BOTTOM_START
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        TextField moduleCoordinatorCompany = new TextField();
        moduleCoordinatorCompany.setLabel("Unternehmen");
        moduleCoordinatorCompany.setPlaceholder("Unternehmen des Ansprechparnters");
        moduleCoordinatorCompany.setReadOnly(true);

        Select<String> selectContact = new Select<>();
        selectContact.setLabel("Ansprechpartner");
        selectContact.setPlaceholder("Ansprechpartner auswählen");
        selectContact.setItems(contactPersonService.getAllContactPersonNames());
        selectContact.addValueChangeListener(event -> {
            contactPerson = contactPersonService.getContactPersonByLastNameAndFirstName(event.getValue());
            moduleCoordinatorCompany.setValue(contactPerson.getCompany().getCompanyName());
        });

        Button buttonNewContact = new Button();
        buttonNewContact.setClassName("student-new-project-form-newcontactbutton");
        buttonNewContact.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Icon icon = new Icon(VaadinIcon.PLUS);
        buttonNewContact.setIcon(icon);
        buttonNewContact.addClickListener(newProjectEvent -> {
            NewContactDialog dialog = new NewContactDialog();
            dialog.open();
        });

        FormLayout projectDetailsBottom = new FormLayout(
                titleGroup, titleContact, buttonNewContact,
                groupMemberOneFhMail, groupMemberOneName, selectContact,
                groupMemberTwoName, groupMemberTwoFhMail, titleCompany,
                groupMemberThreeName, groupMemberThreeFhMail,   moduleCoordinatorCompany
                );

        projectDetailsBottom.setColspan(titleGroup, 2);
        projectDetailsBottom.setColspan(titleCompany, 2);
        projectDetailsBottom.setColspan(moduleCoordinatorCompany, 2);
        projectDetailsBottom.setColspan(selectContact, 2);

        projectDetailsBottom.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        HorizontalLayout buttonBox = new HorizontalLayout();
        buttonBox.setClassName("student-new-project-form-buttonbox");

         /*-----------------------------------------------------------------------------------------------------------
                                                 Projektantrag speichern
         ------------------------------------------------------------------------------------------------------------*/

        Button buttonSave = new Button("Speichern");
        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSave.setClassName("student-new-project-form-button");
        buttonSave.addClickListener(saveContactEvent -> {

            if(projectTitle.getValue() == null
                    || projectSketch.getValue() == null
                    || projectBackground.getValue() == null
                    || pdfByte == null
                    || groupMemberTwo == null
                    || contactPerson == null
            ){
                Notification notification = Notification.show(
                        "Bitte alle Felder ausfüllen",
                        5000,
                        Notification.Position.BOTTOM_START
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            else{
                this.projectDescription = new ProjectDescription(
                        projectTitle.getValue(),
                        projectSketch.getValue(),
                        projectBackground.getValue(),
                        pdfByte
                );

                List<Student> studentList = new ArrayList<>();
                studentList.add(groupMemberOne);
                studentList.add(groupMemberTwo);
                if(!groupMemberThreeName.isEmpty()){
                    studentList.add(groupMemberThree);
                }
                Set<Student> studentSet = new HashSet<>(studentList);

                project.setProjectDescription(this.projectDescription);
                project.setStudents(studentSet);
                project.setContactPerson(contactPerson);

                project.setStatus(Status.ANFRAGE);

                projectService.saveProject(project);

                Notification notification = Notification.show(
                        "Der Projektantrag wurde erfolgreich abgesendet",
                        5000,
                        Notification.Position.BOTTOM_START
                );
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                UI.getCurrent().navigate(StudentProjectOverview.class);
            }
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("student-new-project-form-button");
        buttonCancel.addClickListener(dialogCloseEvent -> UI.getCurrent().navigate(StudentProjectOverview.class));

        buttonBox.add(buttonSave, buttonCancel);

        Div div = new Div(title, moduleText, projectDetailsTop, projectDetailsBottom, buttonBox);
        div.setClassName("student-new-project-form");
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
        moduleText.setText("Modul: " + project.getModuleEnum());
    }
}
