package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.NotificationError;

/**
 * Die Klasse DeleteFreeProjectsDialog öffnet auf der Startseite des Dozenten einen Dialog
 * um neue, freie Projektplätze zu einem Modul zu entfernen, sofern welche vorhanden sind.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@CssImport("/themes/projektverwaltung/components/dozent/delete-free-projects.css")
public class DeleteFreeProjectsDialog extends Dialog {

    public DeleteFreeProjectsDialog(ProjectService projectService, String modulename){

        setWidth("510px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 title = new H1("Projektplätze entfernen");
        title.setClassName("delete-free-projects-title");

        Label modulenameLabel = new Label("Modul: " + modulename);
        modulenameLabel.setClassName("delete-free-projects-modulelabel");

        IntegerField numberOfProjects = new IntegerField();
        numberOfProjects.setValue(projectService.getAllByStatusAndModule(Status.FREI, modulename).size());
        numberOfProjects.setHasControls(true);
        numberOfProjects.setMin(1);
        numberOfProjects.setMax(projectService.getAllByStatusAndModule(Status.FREI, modulename).size());
        numberOfProjects.setLabel("Anzahl der Projektplätze");
        numberOfProjects.setClassName("delete-free-projects-numbers");

        HorizontalLayout buttonBox = new HorizontalLayout();

        Button buttonAddProjects = new Button("Entfernen");
        buttonAddProjects.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonAddProjects.setClassName("delete-free-projects-buttons");
        buttonAddProjects.addClickListener(newProjectEvent -> {
            if(numberOfProjects.getValue() > numberOfProjects.getMax() || numberOfProjects.getValue() < 1){
                NotificationError notificationError = NotificationError.show("Die maximale Anzahl beträgt "
                        + numberOfProjects.getMax() + " !");
                return;
            }
            try {
                projectService.deleteFreeProjects(modulename, numberOfProjects.getValue());
                this.close();
                UI.getCurrent().getPage().reload();
            }
            catch(Exception e){
                NotificationError notificationError = NotificationError.show("Etwas ist schief gelaufen!");
            }
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("delete-free-projects-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        buttonBox.add(buttonAddProjects, buttonCancel);
        buttonBox.addClassName("delete-free-projects-buttonbox");

        Div div = new Div(title, modulenameLabel, numberOfProjects, buttonBox);
        div.addClassName("add-free-projects-dialog");
        add(div);
    }
}
