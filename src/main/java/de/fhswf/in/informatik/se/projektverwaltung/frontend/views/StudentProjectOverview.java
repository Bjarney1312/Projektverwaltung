package de.fhswf.in.informatik.se.projektverwaltung.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.fhswf.in.informatik.se.projektverwaltung.frontend.components.NewProjectRequestDialog;


@RouteAlias(value = "", layout = MainView.class)// später ändern, wenn Rollen dazu kommen
@Route(value = "projektübersicht_student", layout = MainView.class)
@PageTitle("Projektübersicht")
@CssImport("/themes/projektverwaltung/views/student-project-overview.css")
public class StudentProjectOverview extends VerticalLayout {

    public StudentProjectOverview(){
        H1 title = new H1("Projektverwaltung");
        title.setId("title-projektverwaltung-student");

        Grid<String> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setClassName("student-project-overview-grid");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        Button buttonNewProject = new Button("Projektvorschlag");
        buttonNewProject.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNewProject.setClassName("student-projekt-overview-button");
        buttonNewProject.addClickListener(newProjectEvent -> {
            NewProjectRequestDialog dialog = new NewProjectRequestDialog();
            dialog.open();
        });

        Button buttonProjectDetails = new Button("Projekt ansehen");
        buttonProjectDetails.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonProjectDetails.setClassName("student-projekt-overview-button");

        HorizontalLayout buttonBox = new HorizontalLayout();
        buttonBox.setClassName("student-projekt-overview-buttonbox");
        buttonBox.add(buttonNewProject, buttonProjectDetails);

        Div div = new Div(title,grid, buttonBox);
        div.setClassName("student-project-overview");
        add(div);
    }


}
