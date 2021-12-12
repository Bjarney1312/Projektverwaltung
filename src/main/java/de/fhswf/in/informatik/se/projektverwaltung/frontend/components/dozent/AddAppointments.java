package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;

import java.time.Duration;

@CssImport("/themes/projektverwaltung/components/dozent/add-appointments.css")

public class AddAppointments extends Dialog {

    private final ProjectService projectService;

    private Button buttonAddAppointments;

    public AddAppointments(ProjectService projectService){

        this.projectService = projectService;

        setWidth("510px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 title = new H1("Termine vergeben");
        title.setClassName("add-appointments-title");

        DateTimePicker appointmentOne = new DateTimePicker();
        appointmentOne.setLabel("Termin 1");
        appointmentOne.setStep(Duration.ofMinutes(15));
        appointmentOne.setClassName("add-appointments-datetime");

        DateTimePicker appointmentTwo = new DateTimePicker();
        appointmentTwo.setLabel("Termin 2");
        appointmentTwo.setStep(Duration.ofMinutes(15));
        appointmentTwo.setClassName("add-appointments-datetime");

        HorizontalLayout buttonBox = new HorizontalLayout();

        buttonAddAppointments = new Button("Hinzufügen");
        buttonAddAppointments.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonAddAppointments.setClassName("add-appointments-buttons");
        buttonAddAppointments.setEnabled(false);
        buttonAddAppointments.addClickListener(newProjectEvent -> {
            this.close();
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("add-appointments-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        buttonBox.add(buttonAddAppointments, buttonCancel);
        buttonBox.addClassName("add-appointments-buttonbox");

        Div div = new Div(title, appointmentOne, appointmentTwo, buttonBox);
        div.addClassName("add-appointments-dialog");
        add(div);
    }
}
