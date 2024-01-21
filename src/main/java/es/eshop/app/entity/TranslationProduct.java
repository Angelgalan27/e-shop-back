package es.eshop.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "translation_product")
@Data
public class TranslationProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "specifications")
    private String specifications;

    @Column(name = "characteristics")
    private String characteristics;

}
