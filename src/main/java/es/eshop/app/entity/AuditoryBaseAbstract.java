package es.eshop.app.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class AuditoryBaseAbstract {

    @Column(name = "creation_username")
    private String creationUsername;

    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "update_username")
    private String updateUsername;
    @Column(name = "update_date")
    private LocalDate updateDate;
}
