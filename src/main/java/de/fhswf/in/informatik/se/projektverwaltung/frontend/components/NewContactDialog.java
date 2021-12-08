package de.fhswf.in.informatik.se.projektverwaltung.frontend.components;

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

@CssImport("/themes/projektverwaltung/components/new-contact-dialog.css")
public class NewContactDialog extends Dialog {

    public NewContactDialog (){
        setWidth("500px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 titleContact = new H1("Neuer Ansprechpartner");
        titleContact.setClassName("new-contact-dialog-title");

        TextField contactFirstName = new TextField();
        contactFirstName.setLabel("Ansprechpartner");
        contactFirstName.setPlaceholder("Vorname");
        TextField contactLastName = new TextField();
        contactLastName.setPlaceholder("Nachname");

        TextField contactMail = new TextField();
        contactMail.setLabel("Email");
//        contactMail.setPlaceholder("Email");

        TextField contactPhone = new TextField();
        contactPhone.setLabel("Telefon");
//        contactPhone.setPlaceholder("Telefon");

        H1 titleCompany = new H1("Neues Unternehmen");
        titleCompany.setClassName("new-contact-dialog-title");

        TextField companyName = new TextField();
        companyName.setLabel("Unternehmen");
        companyName.setPlaceholder("Name");

        TextField companyAddress = new TextField();
        companyAddress.setLabel("Straße/Hausnummer");
//        companyAddress.setPlaceholder("Straße/Hausnummer");

        TextField companyPlace = new TextField();
        companyPlace.setLabel("Postleitzahl/Ort");
//        companyPlace.setPlaceholder("Ort");

        TextField companyPostal = new TextField();
        companyPostal.setLabel("Postleitzahl/Ort");
//        companyPostal.setPlaceholder("Postleitzahl");

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
        buttonNewCompany.setClassName("new-contact-dialog-companybutton");
        buttonNewCompany.addClickListener(newCompanyEvent -> {
            selectCompany.setValue(null);
            selectCompany.setVisible(false);
            buttonNewCompany.setVisible(false);
            newCompanyForm.setVisible(true);
        });

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

        HorizontalLayout buttonBox = new HorizontalLayout();

        Button buttonChoose = new Button("Speichern");
        buttonChoose.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonChoose.setClassName("new-contact-dialog-button");
        buttonChoose.addClickListener(saveContactEvent -> this.close());

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("new-contact-dialog-button");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        buttonBox.add(buttonChoose, buttonCancel);
        buttonBox.addClassName("new-contact-dialog-buttonbox");

        Div div = new Div(titleContact, newContactForm, buttonNewCompany, newCompanyForm, buttonBox);
        div.addClassName("new-contact-dialog");
        add(div);
    }
}
