package de.fhswf.in.informatik.se.projektverwaltung.frontend.components;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationSuccess extends Notification {

    public NotificationSuccess(){
        setDuration(5000);
        setPosition(Notification.Position.BOTTOM_START);
        addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public static NotificationSuccess show(String text){

        NotificationSuccess notification = new NotificationSuccess();
        notification.setText(text);
        notification.open();
        return notification;
    }
}
