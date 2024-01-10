package es.eshop.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "opinion_product")
@Data
public class OpinionProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "comment")
    private String comment;

    @Column(name = "assessment")
    private Integer assessment;
}
