package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

@CssImport("/themes/projektverwaltung/components/student/edit-contact-dialog.css")
public class EditContactDialog extends Dialog {

    public EditContactDialog(){
        setWidth("500px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 titleContact = new H1("Neuer Ansprechpartner");
        titleContact.setClassName("edit-contact-dialog-titlecontact");

        Select<String> selectContact = new Select<>();
        selectContact.setLabel("Ansprechpartner");
        selectContact.setItems("Max Mustermann");
        selectContact.setClassName("edit-contact-dialog-selectcontact");

        TextField contactFirstName = new TextField();
        contactFirstName.setLabel("Ansprechpartner");
        contactFirstName.setPlaceholder("Vorname");
        TextField contactLastName = new TextField();
        contactLastName.setPlaceholder("Nachname");

        TextField contactMail = new TextField();
        contactMail.setLabel("Email");

        TextField contactPhone = new TextField();
        contactPhone.setLabel("Telefon");

        H1 titleCompany = new H1("Neues Unternehmen");
        titleCompany.setClassName("edit-contact-dialog-titlecompany");

        TextField companyName = new TextField();
        companyName.setLabel("Unternehmen");
        companyName.setPlaceholder("Name");

        TextField companyAddress = new TextField();
        companyAddress.setLabel("Straße/Hausnummer");

        TextField companyPlace = new TextField();
        companyPlace.setLabel("Postleitzahl/Ort");

        TextField companyPostal = new TextField();
        companyPostal.setLabel("Postleitzahl/Ort");

        Select<String> selectCompany = new Select<>();
        selectCompany.setLabel("Unternehmen");
        selectCompany.setItems("FHSWF");

        FormLayout newCompanyForm = new FormLayout(titleCompany, companyName,companyAddress, companyPostal, companyPlace);
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
            newContactForm.setVisible(true);
            newContact.setVisible(false);
            buttonNewCompany.setVisible(true);
            selectContact.setVisible(false);
        });

        HorizontalLayout buttonBox = new HorizontalLayout();

        Button buttonChoose = new Button("Speichern");
        buttonChoose.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonChoose.setClassName("edit-contact-dialog-button");
        buttonChoose.addClickListener(saveContactEvent -> this.close());

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
