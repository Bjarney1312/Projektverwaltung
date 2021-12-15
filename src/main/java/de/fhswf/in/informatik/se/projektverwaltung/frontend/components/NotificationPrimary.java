package de.fhswf.in.informatik.se.projektverwaltung.frontend.components;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationPrimary extends Notification {

    public NotificationPrimary(){
        setDuration(5000);
        setPosition(Notification.Position.BOTTOM_START);
        addThemeVariants(NotificationVariant.LUMO_PRIMARY);
    }

    public static NotificationPrimary show(String text){

        NotificationPrimary notification = new NotificationPrimary();
        notification.setText(text);
        notification.open();
        return notification;
    }
}
