package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.NotificationPrimary;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.dozent.DozentProjectOverview;

/**
 * Die Klasse ProjectModificationDialog öffnet in der View zur Ansicht der Projektdetails beim Dozenten
 * einen Dialog zum Fordern einer Ergänzung zu einem Projekt. Dabei muss der Dozent einen Kommentar
 * abgeben, warum eine Ergänzung gefordert wird.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@CssImport("/themes/projektverwaltung/components/dozent/project-modification-dialog.css")
public class ProjectModificationDialog extends Dialog {

    private static final int COMMENT_MIN_LENGTH = 10;

    public ProjectModificationDialog(ProjectService projectService, Project project){

        setWidth("500px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 title = new H1("Ergänzung fordern");
        title.setClassName("project-modification-title");

        TextArea comment = new TextArea();
        comment.setLabel("Kommentar");
        comment.setClassName("project-modification-comment");
        comment.setValueChangeMode(ValueChangeMode.EAGER);
        comment.setMinLength(COMMENT_MIN_LENGTH);

        HorizontalLayout buttonBox = new HorizontalLayout();

        Button buttonSave = new Button("Senden");
        buttonSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSave.setClassName("project-modification-buttons");
        buttonSave.setEnabled(false);
        buttonSave.addClickListener(newProjectEvent -> {
            project.setComment(comment.getValue());
            projectService.saveProject(project);
            NotificationPrimary notificationAccept = NotificationPrimary.show("Es wurde eine Ergänzung zu dem Projekt gefordert.");
            this.close();
            UI.getCurrent().navigate(DozentProjectOverview.class);
        });
        comment.addValueChangeListener(addCommentEvent -> buttonSave.setEnabled(
                !addCommentEvent.getValue().isEmpty() &&
                        addCommentEvent.getValue().length() >= COMMENT_MIN_LENGTH));

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("project-modification-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        buttonBox.add(buttonSave, buttonCancel);
        buttonBox.addClassName("project-modification-buttonbox");

        Div div = new Div(title, comment, buttonBox);
        div.addClassName("project-modification-dialog");
        add(div);
    }
}
