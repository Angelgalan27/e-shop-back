package es.eshop.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_address")
@Data
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name="city_id", nullable = false)
    private City city;

    @Column(name = "main")
    private Boolean main;

    @Column(name = "street")
    private String street;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "cp")
    private Integer cp;

    @Column(name = "user_address_name")
    private String userAddressName;

    @Column(name = "user_address_surname")
    private String userAddressSurname;

    @Column(name = "observations")
    private String observations;


}
