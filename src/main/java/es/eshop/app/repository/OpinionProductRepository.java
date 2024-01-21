package es.eshop.app.repository;

import es.eshop.app.entity.OpinionProduct;
import es.eshop.app.entity.Product;
import es.eshop.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpinionProductRepository extends JpaRepository<OpinionProduct, Long> {

    List<OpinionProduct> findByUser(User user);

    List<OpinionProduct> findByProduct(Product product);

    List<OpinionProduct> findByUserAndProduct(User user, Product product);
}
