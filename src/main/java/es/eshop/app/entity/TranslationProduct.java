package es.eshop.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "translation_product")
public class TranslationProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "description")
    private String description;

    @Column(name = "specifications")
    private String specifications;

    @Column(name = "characteristics")
    private String characteristics;

}
