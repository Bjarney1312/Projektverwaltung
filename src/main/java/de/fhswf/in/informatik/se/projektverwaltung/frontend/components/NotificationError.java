package de.fhswf.in.informatik.se.projektverwaltung.frontend.components;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationError extends Notification {

    public NotificationError(){
        setDuration(5000);
        setPosition(Notification.Position.BOTTOM_START);
        addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    public static NotificationError show(String text){

        NotificationError notification = new NotificationError();
        notification.setText(text);
        notification.open();
        return notification;
    }
}
