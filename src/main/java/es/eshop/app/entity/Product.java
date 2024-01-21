package es.eshop.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "stock")
    private Integer stock;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_file", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<TranslationProduct> translations = new ArrayList<>();
}
