package de.fhswf.in.informatik.se.projektverwaltung.frontend.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.RouteParameters;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.views.StudentNewProjectForm;

@CssImport("/themes/projektverwaltung/components/new-project-dialog.css")
public class NewProjectRequestDialog extends Dialog {

    public NewProjectRequestDialog(){

        setWidth("500px");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);

        H1 title = new H1("Projektplatz auswählen");
        title.setClassName("new-project-dialog-title");

        Select<String> selectModule = new Select<>();
        selectModule.setClassName("new-project-dialog-select");
        selectModule.setLabel("Modul");
        selectModule.setItems("Software Engineering");
        selectModule.setValue("Software Engineering");

        Grid<String> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setClassName("new-project-dialog-grid");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        HorizontalLayout buttonBox = new HorizontalLayout();

        Button buttonChoose = new Button("Auswählen");
        buttonChoose.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonChoose.setClassName("new-project-dialog-buttons");
        buttonChoose.addClickListener(newProjectEvent -> {
//            Hier muss später das Modul mitgegeben werden
            UI.getCurrent().navigate(StudentNewProjectForm.class, new RouteParameters("module", selectModule.getValue()));
//            UI.getCurrent().navigate(StudentNewProjectForm.class);
            this.close();
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonCancel.setClassName("new-project-dialog-buttons");
        buttonCancel.addClickListener(dialogCloseEvent -> this.close());

        buttonBox.add(buttonChoose, buttonCancel);
        buttonBox.addClassName("new-project-dialog-buttonbox");

        Div div = new Div(title, selectModule, grid, buttonBox);
        div.addClassName("new-project-dialog");
        add(div);
    }
}
