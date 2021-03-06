package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.enums.ModuleEnum;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ModuleCoordinatorService;
import de.fhswf.in.informatik.se.projektverwaltung.backend.services.ProjectService;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.NotificationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse AddFreeProjectsDialog öffnet auf der Startseite des Dozenten einen Dialog
 * um neue, freie Projektplätze zu einem Modul zu vergeben.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@CssImport("/themes/projektverwaltung/components/dozent/add-free-projects.css")
public class AddFreeProjectsDialog extends Dialog {

    private Button buttonAddProjects;

    public AddFreeProjectsDialog(ModuleCoordinatorService moduleCoordinatorService, ProjectService projectService){

        setWidth("510px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 title = new H1("Projektplätze hinzufügen");
        title.setClassName("add-free-projects-title");

        Select<String> selectModule = new Select<>();
        selectModule.setClassName("add-free-projects-select");
        selectModule.setLabel("Modul");
        List<String> moduleList = new ArrayList<>();
        for(ModuleEnum module : moduleCoordinatorService.findByUsername().getModul()){
            moduleList.add(module.label);
        }
        selectModule.setItems(moduleList);
        selectModule.setPlaceholder("Modul auswählen");
        selectModule.addValueChangeListener(changeEvent -> {
            if(changeEvent.getValue() != null){
                buttonAddProjects.setEnabled(true);
            }
        });

        IntegerField numberOfProjects = new IntegerField();
        numberOfProjects.setValue(10);
        numberOfProjects.setHasControls(true);
        numberOfProjects.setMin(1);
        numberOfProjects.setMax(100);
        numberOfProjects.setLabel("Anzahl der Projektplätze");
        numberOfProjects.setClassName("add-free-projects-numbers");

        HorizontalLayout buttonBox = new HorizontalLayout();

        buttonAddProjects = new Button("Hinzufügen");
        buttonAddProjects.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonAddProjects.setClassName("add-free-projects-buttons");
        buttonAddProjects.setEnabled(false);
        buttonAddProjects.addClickListener(newProjectEvent -> {
            if(numberOfProjects.getValue() > numberOfProjects.getMax() || numberOfProjects.getValue() < 1){
                NotificationError notificationError = NotificationError.show("Die maximale Anzahl beträgt " + numberOfProjects.getMax() + " !");
                return;
            }
            projectService.initializeProjects(moduleCoordinatorService.findByUsername().getModulByModulName(
                    selectModule.getValue()), numberOfProjects.getValue());
            this.close();
            UI.getCurrent().getPage().reload();
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("add-free-projects-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        buttonBox.add(buttonAddProjects, buttonCancel);
        buttonBox.addClassName("add-free-projects-buttonbox");

        Div div = new Div(title, selectModule, numberOfProjects, buttonBox);
        div.addClassName("add-free-projects-dialog");
        add(div);
    }
}
