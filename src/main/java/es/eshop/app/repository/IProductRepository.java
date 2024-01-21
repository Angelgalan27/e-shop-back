package es.eshop.app.repository;

import es.eshop.app.entity.Product;
import es.eshop.app.model.FilterProductDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("FROM Product p " +
            "WHERE (:#{#filter.name} IS NULL OR p.name like %:#{#filter.name}%) " +
            "AND (:#{#filter.code} IS NULL OR p.code = :#{#filter.code}) " +
            "AND (:#{#filter.partNumber} IS NULL OR p.partNumber = :#{#filter.partNumber})")
    Page<Product> getAllByFilter(@NotNull @Param("filter") FilterProductDTO filter, Pageable pageable);
}
