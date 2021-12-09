package de.fhswf.in.informatik.se.projektverwaltung.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.*;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.StudentService;


@Route(value = "projektantrag_student/:module", layout = MainView.class)
@PageTitle("Projektantrag")
@CssImport("/themes/projektverwaltung/views/student-new-project-form.css")
public class StudentNewProjectForm extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private String module;
    private Label moduleText;

    public StudentNewProjectForm(){

        H1 title = new H1("Projekt Details");

        moduleText = new Label("Modul: ");

        TextField projectTitle = new TextField();
        projectTitle.setLabel("Projekttitel");
        projectTitle.addClassName("student-new-project-form-input");

        TextArea projectSketch = new TextArea();
        projectSketch.setLabel("Kurze Skizze");
        projectSketch.addClassName("student-new-project-form-input");

        TextArea projectBackground = new TextArea();
        projectBackground.setLabel("Beschreibung des Projekthintergrundes");
        projectBackground.addClassName("student-new-project-form-input");

        Label projectDescription = new Label("Beschreibung der wesentlichen Projektinhalte");
        projectDescription.setClassName("student-new-project-form-upload-label");

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("application/pdf", ".pdf");
        upload.setMaxFiles(1);
        Button uploadButton = new Button("Upload PDF...");
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        uploadButton.setClassName("student-new-project-form-upload-button");
        upload.setUploadButton(uploadButton);
        upload.setClassName("student-new-project-form-upload");

        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(
                    errorMessage,
                    5000,
                    Notification.Position.MIDDLE
            );
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });


        FormLayout projectDetails = new FormLayout(projectTitle, projectSketch, projectBackground, projectDescription, upload);
        projectDetails.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        projectDetails.setClassName("student-new-project-form-upperformlayout");



        Div div = new Div(title,moduleText, projectDetails);
        div.setClassName("student-new-project-form");
        add(div);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getRouteParameters().get("module").isPresent()){
            module = beforeEnterEvent.getRouteParameters().get("module").get();
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        this.moduleText.setText("Modul: " + this.module);
//        drive= interactor.displayFahrerRouteById(Integer.parseInt(itemID));
//        createOwnDriveOffersEditView();
//        createButtons();
    }
}
