package es.eshop.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "city")
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false, insertable = false, updatable = false)
    private Province province;

    @Column(name = "code")
    private Integer code;

    @Column(name = "name")
    private String name;
}
