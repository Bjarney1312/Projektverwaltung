package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Company;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.ContactPerson;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.CompanyService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ContactPersonService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.NotificationError;

/**
 * Die Klasse EditContactDialog öffnet in der View zur Ansicht der Projektdetails beim Studenten
 * einen Dialog zum Bearbeiten des Ansprechpartners. Hierbei kann ein bestehender Ansprechpartner
 * gewählt werden, oder ein neuer angelegt werden. Außerdem kann bei einem neuen Ansprechpartner
 * ein bestehendes Unternehmen zugeordnet werden, oder ebenfalls ein neues erfasst werden.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@CssImport("/themes/projektverwaltung/components/student/edit-contact-dialog.css")
public class EditContactDialog extends Dialog {

    private ContactPerson contactPerson;
    private Company company;
    private int apfel;

    public EditContactDialog(Project project, ProjectService projectService, ContactPersonService contactPersonService, CompanyService companyService) {

        apfel = 0;
        setWidth("500px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 titleContact = new H1("Neuer Ansprechpartner");
        titleContact.setClassName("edit-contact-dialog-titlecontact");

        Select<String> selectContact = new Select<>();
        selectContact.setLabel("Ansprechpartner");
        selectContact.setClassName("edit-contact-dialog-selectcontact");
        selectContact.setItems(contactPersonService.getAllContactPersonNames());
        selectContact.setValue(project.getContactPerson().getLastName() + ", " + project.getContactPerson().getFirstName());

        selectContact.addValueChangeListener(event -> contactPerson = contactPersonService.getContactPersonByLastNameAndFirstName(event.getValue()));

        //Wenn man neu erstellt
        TextField contactFirstName = new TextField();
        contactFirstName.setLabel("Ansprechpartner");
        contactFirstName.setPlaceholder("Vorname");
        TextField contactLastName = new TextField();
        contactLastName.setPlaceholder("Nachname");

        EmailField contactMail = new EmailField();
        contactMail.setLabel("Email");
        contactMail.setClearButtonVisible(true);
        contactMail.setErrorMessage("Bitte eine gültige Mail angeben");

        TextField contactPhone = new TextField();
        contactPhone.setLabel("Telefon");
        contactPhone.setPattern("\\d*");
        contactPhone.setPreventInvalidInput(true);

        H1 titleCompany = new H1("Neues Unternehmen");
        titleCompany.setClassName("edit-contact-dialog-titlecompany");

        TextField companyName = new TextField();
        companyName.setLabel("Unternehmen");
        companyName.setPlaceholder("Name");

        TextField companyAddress = new TextField();
        companyAddress.setLabel("Straße/Hausnummer");

        TextField companyPlace = new TextField();
        companyPlace.setLabel("Ort");

        TextField companyPostal = new TextField();
        companyPostal.setLabel("Postleitzahl");
        companyPostal.setPattern("\\d*");
        companyPostal.setPreventInvalidInput(true);

        Select<String> selectCompany = new Select<>();
        selectCompany.setLabel("Unternehmen");
        selectCompany.setItems(companyService.getAllCompanyNames());
        selectCompany.setPlaceholder("Unternehmen auswählen");


        FormLayout newCompanyForm = new FormLayout(titleCompany, companyName, companyAddress, companyPostal, companyPlace);
        newCompanyForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        newCompanyForm.setColspan(titleCompany, 2);
        newCompanyForm.setColspan(companyName, 2);
        newCompanyForm.setColspan(companyAddress, 2);
        newCompanyForm.setVisible(false);

        Button buttonNewCompany = new Button("Neues Unternehmen");
        buttonNewCompany.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNewCompany.setClassName("edit-contact-dialog-addnewbutton");
        buttonNewCompany.addClickListener(newCompanyEvent -> {
            selectCompany.setValue(null);
            selectCompany.setVisible(false);
            buttonNewCompany.setVisible(false);
            newCompanyForm.setVisible(true);
            apfel = 2;
        });
        buttonNewCompany.setVisible(false);

        FormLayout newContactForm = new FormLayout(
                contactFirstName, contactLastName,
                contactMail, contactPhone,
                selectCompany
        );

        newContactForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        newContactForm.setColspan(selectCompany, 2);
        newContactForm.setColspan(buttonNewCompany, 2);
        newContactForm.setVisible(false);

        Button newContact = new Button("Neuer Ansprechpartner");
        newContact.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newContact.setClassName("edit-contact-dialog-addnewbutton");
        newContact.addClickListener(newCompanyEvent -> {
            apfel = 1;
            newContactForm.setVisible(true);
            newContact.setVisible(false);
            buttonNewCompany.setVisible(true);
            selectContact.setVisible(false);
        });

        HorizontalLayout buttonBox = new HorizontalLayout();

        Button buttonChoose = new Button("Speichern");
        buttonChoose.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonChoose.setClassName("edit-contact-dialog-button");
        buttonChoose.addClickListener(saveContactEvent -> {

            switch (apfel) {
                case 0 -> {
                    if (contactPerson == null) {
                        NotificationError notificationError = NotificationError.show("Der Ansprechpartner ist schon dem Projekt zugeordnet!");
                        break;
                    }
                    project.setContactPerson(contactPerson);
                    projectService.saveProject(project);
                    this.close();
                    UI.getCurrent().getPage().reload();

                }
                case 1 -> {
                    if (contactFirstName.isEmpty() || contactLastName.isEmpty() ||
                            selectCompany.isEmpty() || contactMail.isEmpty()) {
                        NotificationError notificationError = NotificationError.show("Bitte alle Felder ausfüllen!");
                        break;
                    }
                    contactPerson = new ContactPerson(
                            contactFirstName.getValue(),
                            contactLastName.getValue(),
                            companyService.getCompanyByCompanyName(
                                    selectCompany.getValue()),
                                    contactMail.getValue(),
                                    contactPhone.getValue());
                    project.setContactPerson(contactPerson);
                    contactPersonService.saveContactPerson(contactPerson);
                    projectService.saveProject(project);
                    this.close();
                    UI.getCurrent().getPage().reload();

                }
                case 2 -> {
                    if (contactFirstName.isEmpty() || contactLastName.isEmpty() || contactMail.isEmpty()
                            || companyName.isEmpty() || companyAddress.isEmpty()
                            || companyPostal.isEmpty() || companyPlace.isEmpty()) {

                        NotificationError notificationError = NotificationError.show("Bitte alle Felder ausfüllen!");
                        break;
                    }
                    company = new Company(
                            companyName.getValue(),
                            companyAddress.getValue(),
                            Integer.parseInt(companyPostal.getValue()),
                            companyPlace.getValue());
                    contactPerson = new ContactPerson(
                            contactFirstName.getValue(),
                            contactLastName.getValue(),
                            company,
                            contactMail.getValue(),
                            contactPhone.getValue());
                    project.setContactPerson(contactPerson);
                    companyService.saveCompany(company);
                    contactPersonService.saveContactPerson(contactPerson);
                    projectService.saveProject(project);
                    this.close();
                    UI.getCurrent().getPage().reload();

                }
                default -> {
                    NotificationError notificationError = NotificationError.show("Es ist etwas schief gelaufen");
                }
            }
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("edit-contact-dialog-button");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        buttonBox.add(buttonChoose, buttonCancel);
        buttonBox.addClassName("edit-contact-dialog-buttonbox");

        Div div = new Div(titleContact, selectContact, newContact, newContactForm, buttonNewCompany, newCompanyForm, buttonBox);
        div.addClassName("edit-contact-dialog");
        add(div);
    }
}
