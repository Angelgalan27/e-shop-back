package es.eshop.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.security.PrivateKey;

@Entity
@Table(name = "province")
@Data
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false, updatable = false, insertable = false)
    private Country country;

    @Column(name = "code")
    private Integer code;

    @Column(name = "name")
    private String name;
}
