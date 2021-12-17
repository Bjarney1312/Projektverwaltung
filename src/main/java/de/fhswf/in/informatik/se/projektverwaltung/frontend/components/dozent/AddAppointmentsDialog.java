package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Die Klasse AddAppointmentsDialog öffnet auf der Startseite des Dozenten einen Dialog
 * um zu einem ausgewählten, angenommenen Projekt Vortragstermine zu vergeben.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@CssImport("/themes/projektverwaltung/components/dozent/add-appointments.css")
public class AddAppointmentsDialog extends Dialog {

    private final ProjectService projectService;
    private Project project;

    private Button buttonAddAppointments;

    public AddAppointmentsDialog(ProjectService projectService, Long projectId){

        this.projectService = projectService;
        projectService.findProjectById(projectId).ifPresent(project -> this.project = project);

        setWidth("510px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 title = new H1("Termine vergeben");
        title.setClassName("add-appointments-title");

        DateTimePicker appointmentOne = new DateTimePicker();
        appointmentOne.setLabel("Termin 1");
        appointmentOne.setMin(LocalDateTime.now());
        appointmentOne.setWeekNumbersVisible(true);
        appointmentOne.setDatePickerI18n(new DatePicker.DatePickerI18n().setFirstDayOfWeek(1));
        appointmentOne.setStep(Duration.ofMinutes(15));
        appointmentOne.setClassName("add-appointments-datetime");
        appointmentOne.addValueChangeListener(appointmentOneEvent -> {
            buttonAddAppointments.setEnabled(true);
            project.getPresentationDates().setTermin1(appointmentOneEvent.getValue());
        });

        DateTimePicker appointmentTwo = new DateTimePicker();
        appointmentTwo.setLabel("Termin 2");
        appointmentTwo.setMin(LocalDateTime.now());
        appointmentTwo.setDatePickerI18n(new DatePicker.DatePickerI18n().setFirstDayOfWeek(1));
        appointmentTwo.setWeekNumbersVisible(true);
        appointmentTwo.setStep(Duration.ofMinutes(15));
        appointmentTwo.setClassName("add-appointments-datetime");
        appointmentTwo.addValueChangeListener(appointmentTwoEvent -> {
            buttonAddAppointments.setEnabled(true);
            project.getPresentationDates().setTermin2(appointmentTwoEvent.getValue());
        });

        HorizontalLayout buttonBox = new HorizontalLayout();

        buttonAddAppointments = new Button("Hinzufügen");
        buttonAddAppointments.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonAddAppointments.setClassName("add-appointments-buttons");
        buttonAddAppointments.setEnabled(false);
        buttonAddAppointments.addClickListener(newProjectEvent -> {
            projectService.saveProject(project);
            this.close();
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("add-appointments-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        if(project.getPresentationDates().getTermin1() != null){
            appointmentOne.setValue(project.getPresentationDates().getTermin1());
            appointmentOne.setEnabled(false);
            buttonAddAppointments.setEnabled(false);

            if(project.getPresentationDates().getTermin2() != null){
                appointmentTwo.setValue(project.getPresentationDates().getTermin2());
                appointmentTwo.setEnabled(false);
                buttonAddAppointments.setEnabled(false);
            }
        }

        buttonBox.add(buttonAddAppointments, buttonCancel);
        buttonBox.addClassName("add-appointments-buttonbox");

        Div div = new Div(title, appointmentOne, appointmentTwo, buttonBox);
        div.addClassName("add-appointments-dialog");
        add(div);
    }
}
