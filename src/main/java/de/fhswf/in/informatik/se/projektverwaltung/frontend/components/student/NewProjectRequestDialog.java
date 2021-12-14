package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.RouteParameters;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.Status;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.student.StudentNewProjectForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse NewProjectRequestDialog öffnet einen Dialog, in dem der
 * Student ein Modul auswählen kann, zu dem er ein Projekt bearbeiten
 * möchte. Dem Studenten wird angezeigt, ob zu dem gewählten Modul ein
 * Platz verfügbar ist oder nicht. Ist ein Platz frei, kann er über einen
 * Button den Projektantrag erreichen.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@CssImport("/themes/projektverwaltung/components/student/new-project-dialog.css")
public class NewProjectRequestDialog extends Dialog {

    private final Label moduleProjectInformation;
    private final ProjectService projectService;
    private Long projectId;
    private Button buttonChoose;

    public NewProjectRequestDialog(ProjectService projectService, List<String> studentModules){

        this.projectService = projectService;

        setWidth("500px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 title = new H1("Modul wählen");
        title.setClassName("new-project-dialog-title");

        Select<String> selectModule = new Select<>();
        selectModule.setClassName("new-project-dialog-select");
        selectModule.setLabel("Modul");
        List<String> moduleList = new ArrayList<>();
        for(ModuleEnum module : ModuleEnum.values()){
            moduleList.add(module.label);
        }
        selectModule.setItems(moduleList);
        selectModule.setPlaceholder("Modul auswählen");

        moduleProjectInformation = new Label();
        moduleProjectInformation.setClassName("new-project-dialog-label");

        selectModule.addValueChangeListener(event -> {
            if(studentModules.contains(selectModule.getValue())){
                moduleProjectInformation.setText("Projekt für " + selectModule.getValue() + " bereits vorhanden.");
                moduleProjectInformation.getStyle().set("color", "red");
                buttonChoose.setEnabled(false);
            }
            else {
                try {
                    projectId = this.projectService.getProjectByStatusAndModuleEnum(Status.FREI, event.getValue()).getId();
                    moduleProjectInformation.setText("Es ist ein freier Projektplatz verfügbar");
                    moduleProjectInformation.getStyle().set("color", "darkgreen");
                    buttonChoose.setEnabled(true);
                } catch (Exception e) {
                    moduleProjectInformation.setText(e.getMessage());
                    moduleProjectInformation.getStyle().set("color", "red");
                    buttonChoose.setEnabled(false);
                }
            }
        });

        HorizontalLayout buttonBox = new HorizontalLayout();

        buttonChoose = new Button("Antrag stellen");
        buttonChoose.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonChoose.setClassName("new-project-dialog-buttons");
        buttonChoose.setEnabled(false);
        buttonChoose.addClickListener(newProjectEvent -> {
            UI.getCurrent().navigate(StudentNewProjectForm.class, new RouteParameters("projectid", projectId.toString()));
            this.close();
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("new-project-dialog-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        buttonBox.add(buttonChoose, buttonCancel);
        buttonBox.addClassName("new-project-dialog-buttonbox");

        Div div = new Div(title, selectModule, moduleProjectInformation, buttonBox);
        div.addClassName("new-project-dialog");
        add(div);
    }
}
