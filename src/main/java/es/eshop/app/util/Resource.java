package es.eshop.app.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Resource {

    private static final ResourceBundle resource = ResourceBundle.getBundle("message_es");

    public static String getMessage(String clave) {
        return resource.getString(clave);
    }

    public static String getMessage(String clave, Object... args) {
        return MessageFormat.format(resource.getString(clave), args);
    }
}
