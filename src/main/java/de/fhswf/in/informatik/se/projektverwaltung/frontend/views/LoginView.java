package de.fhswf.in.informatik.se.projektverwaltung.frontend.views;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

/**
 * Die Klasse LoginView erstellt den Login der Applikation.
 *
 * @author Ivonne Kneißig & Ramon Günther
 */
@RouteAlias(value = "")
@Route("login")
@PageTitle("Projektverwaltung | Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login;

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login = new LoginForm();
        login.setAction("login");

        add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
