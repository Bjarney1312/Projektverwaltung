package de.fhswf.in.informatik.se.projektverwaltung.frontend.views.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
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
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.*;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student.NewContactDialog;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.MainView;

@Route(value = "projektantrag_student/:module", layout = MainView.class)
@PageTitle("Projektverwaltung | Projektantrag")
@CssImport("/themes/projektverwaltung/views/student/student-new-project-form.css")
public class StudentNewProjectForm extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private String module;
    private Label moduleText;

    public StudentNewProjectForm(){

         /*-----------------------------------------------------------------------------------------------------------
                                                      Oberer Teil
         ------------------------------------------------------------------------------------------------------------*/

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

        FormLayout projectDetailsTop = new FormLayout(projectTitle, projectSketch, projectBackground, projectDescription, upload);
        projectDetailsTop.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        projectDetailsTop.setClassName("student-new-project-form-upperformlayout");

        /*-----------------------------------------------------------------------------------------------------------
                                                      Unterer Teil
         ------------------------------------------------------------------------------------------------------------*/

        H2 titleGroup = new H2("Gruppe");
        titleGroup.setClassName("test");
        H2 titleContact = new H2("Ansprechpartner");
        titleContact.setClassName("test");
        H2 titleCompany = new H2("Unternehmen");
        titleCompany.setClassName("test");

        TextField groupMemberOneFirstName = new TextField();
        groupMemberOneFirstName.setLabel("Gruppenmitglied 1");
        groupMemberOneFirstName.setPlaceholder("Vorname");
        TextField groupMemberOneLastName = new TextField();
        groupMemberOneLastName.setPlaceholder("Nachname");

        TextField groupMemberTwoFirstName = new TextField();
        groupMemberTwoFirstName.setLabel("Gruppenmitglied 2");
        groupMemberTwoFirstName.setPlaceholder("Vorname");
        TextField groupMemberTwoLastName = new TextField();
        groupMemberTwoLastName.setPlaceholder("Nachname");

        TextField groupMemberThreeFirstName = new TextField();
        groupMemberThreeFirstName.setLabel("Gruppenmitglied 3");
        groupMemberThreeFirstName.setPlaceholder("Vorname");
        TextField groupMemberThreeLastName = new TextField();
        groupMemberThreeLastName.setPlaceholder("Nachname");



        Select<String> selectContact = new Select<>();
        selectContact.setLabel("Ansprechpartner");
        selectContact.setItems("Tobias Holke", "Matthias Faulstich");

        Select<String> selectCompany = new Select<>();
        selectCompany.setLabel("Unternehmen");
        selectCompany.setItems("FHSWF");

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
                groupMemberOneFirstName, groupMemberOneLastName, selectContact,
                groupMemberTwoFirstName, groupMemberTwoLastName, titleCompany,
                groupMemberThreeFirstName, groupMemberThreeLastName,   selectCompany
                );

        projectDetailsBottom.setColspan(titleGroup, 2);
        projectDetailsBottom.setColspan(titleCompany, 2);
        projectDetailsBottom.setColspan(selectCompany, 2);
        projectDetailsBottom.setColspan(selectContact, 2);

        projectDetailsBottom.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP));


        HorizontalLayout buttonBox = new HorizontalLayout();
        buttonBox.setClassName("student-new-project-form-buttonbox");

        Button buttonChoose = new Button("Speichern");
        buttonChoose.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonChoose.setClassName("student-new-project-form-button");
//        buttonChoose.addClickListener(saveContactEvent -> this.close());

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("student-new-project-form-button");
        buttonCancel.addClickListener(dialogCloseEvent -> UI.getCurrent().navigate(StudentProjectOverview.class));

        buttonBox.add(buttonChoose, buttonCancel);

        Div div = new Div(title, moduleText, projectDetailsTop, projectDetailsBottom, buttonBox);
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
