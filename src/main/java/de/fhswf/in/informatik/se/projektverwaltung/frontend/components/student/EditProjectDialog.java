package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@CssImport("/themes/projektverwaltung/components/student/edit-project-form.css")
public class EditProjectDialog extends Dialog {

    private final ProjectService projectService;
    private byte[] pdfByte;
    private final Anchor uploadPreview;
    private final TextField projectTitle;
    private final TextArea projectSketch;
    private final TextArea projectBackground;

    public EditProjectDialog(ProjectService projectService, Project project){

        this.projectService = projectService;

        setWidth("1000px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 title = new H1("Projekt bearbeiten");
        title.addClassName("edit-project-form-title");

        projectTitle = new TextField();
        projectTitle.setLabel("Projekttitel");
        projectTitle.addClassName("edit-project-form-input");

        projectSketch = new TextArea();
        projectSketch.setLabel("Kurze Skizze");
        projectSketch.addClassName("edit-project-form-input");

        projectBackground = new TextArea();
        projectBackground.setLabel("Beschreibung des Projekthintergrundes");
        projectBackground.addClassName("edit-project-form-input");

        Label projectDescription = new Label("Beschreibung der wesentlichen Projektinhalte");
        projectDescription.setClassName("edit-project-form-upload-label");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("application/pdf", ".pdf");
        upload.setMaxFiles(1);
        int maxFileSizeInBytes = 1024 * 1024 * 5;
        upload.setMaxFileSize(maxFileSizeInBytes);

        Button uploadButton = new Button("Upload PDF...");
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        uploadButton.setClassName("edit-project-form-upload-button");
        upload.setUploadButton(uploadButton);
        upload.setClassName("edit-project-form-upload");

        uploadPreview = new Anchor();
        uploadPreview.setClassName("edit-project-form-uploadpreview");

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
        projectDetailsTop.setClassName("edit-project-form-upperformlayout");


        Button buttonSave = new Button("Speichern");
        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSave.setClassName("student-new-project-form-button");
        buttonSave.addClickListener(saveContactEvent -> {

        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("student-new-project-form-button");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        HorizontalLayout buttonBox = new HorizontalLayout();
        buttonBox.setClassName("student-new-project-form-buttonbox");

        buttonBox.add(buttonSave, buttonCancel);

        Div div = new Div(title, projectDetailsTop, buttonBox);
        div.setClassName("edit-project-form");
        add(div);
   }
}
