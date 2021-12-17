package de.fhswf.in.informatik.se.projektverwaltung.frontend.components.dozent;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Project;
import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Die Klasse GroupMemberLayout erstellt ein HorizontalLayout mit den
 * Namen der Gruppenmitglieder des jeweiligen Projekts, um es in der
 * Tabelle in der Klasse DozentProjectOverView anzeigen zu lassen
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
public class GroupMemberLayout extends HorizontalLayout {
    private final TextField groupMember1 = new TextField("Gruppenmitglied 1");
    private final TextField groupMember2 = new TextField("Gruppenmitglied 2");
    private final TextField groupMember3 = new TextField("Gruppenmitglied 3");


    public GroupMemberLayout() {
        Stream.of(groupMember1, groupMember2, groupMember3).forEach(field -> {
            field.setReadOnly(true);
            add(field);
        });
    }


    public void setGroupMember(Project project) {

        List<Student> students = new ArrayList<>(project.getStudents());

        groupMember1.setValue(students.get(0).getLastName() + ", " + students.get(0).getFirstName());
        groupMember2.setValue(students.get(1).getLastName() + ", " + students.get(1).getFirstName());

        if (students.size() == 3) {
            groupMember3.setValue(students.get(2).getLastName() + ", " + students.get(2).getFirstName());
        } else {
            groupMember3.setVisible(false);
        }
    }
}

