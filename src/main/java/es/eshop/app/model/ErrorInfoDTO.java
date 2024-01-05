package es.eshop.app.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorInfoDTO {

    private String description;

    private Integer code;

    private String url;

    private LocalDateTime date;
}
