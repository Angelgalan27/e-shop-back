package es.eshop.app.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;

public class StringUtil {


    /**
     * Obtiene la extensión del archivo.
     * @param name
     * @return
     */
    public static MediaType getExtension(String name) {
       return switch (FilenameUtils.getExtension(name)) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif"  -> MediaType.IMAGE_GIF;
            case "pdf"  -> MediaType.APPLICATION_PDF;
            default -> MediaType.parseMediaType("application/octet-stream");
        };

    }

}
