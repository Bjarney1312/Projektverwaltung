package de.fhswf.in.informatik.se.projektverwaltung.frontend.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.ProjectRepository;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.CompanyService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ContactPersonService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student.EditContactDialog;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@CssImport("/themes/projektverwaltung/components/project-details.css")
public class ProjectDetails extends VerticalLayout {

    private Button buttonEditContact;

    public ProjectDetails(Project project, ProjectService projectService, ContactPersonService contactPersonService, CompanyService companyService){
        H1 title = new H1("Projektdetails");
        title.setId("project-details-title");

        //Modul
        H2 module = new H2("Modul");
        Label moduleName = new Label(project.getModule());
        moduleName.setClassName("project-details-h2");

        H2 contact = new H2("Ansprechpartner" );
        buttonEditContact = new Button();
        buttonEditContact.setIcon(new Icon(VaadinIcon.PENCIL));
        buttonEditContact.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        buttonEditContact.setClassName("project-details-contacteditbutton");
        buttonEditContact.addClickListener(editContactEvent -> {
            EditContactDialog editContact = new EditContactDialog(project, projectService, contactPersonService, companyService);
            editContact.open();
        });
        HorizontalLayout layoutConctactTitleWithButton = new HorizontalLayout(contact, buttonEditContact);
        layoutConctactTitleWithButton.setClassName("project-details-contacteditlayout");

        contact.setClassName("project-details-h2");
        Label contactName = new Label(project.getContactPerson().getLastName()
                + ", " + project.getContactPerson().getFirstName());

        Label contactMail = new Label(project.getContactPerson().getEmail());

        //Unternehmen
        H2 company = new H2("Unternehmen");
        Label companyName = new Label(project.getContactPerson().getCompany().getCompanyName());
        Label companyStreet = new Label(project.getContactPerson().getCompany().getStreet());
        Label companyPlace = new Label(project.getContactPerson().getCompany().getPostalCode() + " "
                + project.getContactPerson().getCompany().getLocation());

        //Gruppenmitglieder
        H2 group = new H2("Gruppenmitglieder");
        Student[] students = project.getStudents().toArray(new Student[0]);
        Label groupMemberOne = new Label (students[0].getFirstName() + " " +  students[0].getLastName());
        Label groupMemberTwo = new Label(students[1].getFirstName() + " " +  students[1].getLastName());
        Label groupMemberThree = new Label();
        if(students.length == 3){
            groupMemberThree.setText(students[2].getFirstName() + " " + students[2].getLastName());
        }

        //Termine
        H2 appointments = new H2("Termine");

        Label appointmentOne = new Label("-");

        Label appointmentTwo = new Label("-");

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("de"));

        //TODO: In PresentationDates direkt formattiert zurückgeben!

        if(project.getPresentationDates().getTermin1() != null){
            appointmentOne.setText(project.getPresentationDates().getTermin1().format(formatter));
        }

        if(project.getPresentationDates().getTermin2() != null){
            appointmentOne.setText(project.getPresentationDates().getTermin2().format(formatter));
        }

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

        H3 commentTitle = new H3("Kommentar");
        Label comment = new Label(project.getComment());

        FormLayout projectDetailsTop = new FormLayout(
                module, layoutConctactTitleWithButton,
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
        projectDetailsTop.setClassName("project-details-upperformlayout");

        FormLayout projectDetailsBottom = new FormLayout(
                projectTitle,
                sketchTitle,
                projectSketch,
                backgroundTitle,
                projectBackground,
                descriptopnTitle,
                projectDescription,
                commentTitle,
                comment
        );
        projectDetailsBottom.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        projectDetailsBottom.setClassName("project-details-lowerformlayout");

        Div div = new Div(title, projectDetailsTop, projectDetailsBottom);
        div.setClassName("project-details");
        add(div);
    }

    public Button getButtonEditContact() {
        return buttonEditContact;
    }
}
