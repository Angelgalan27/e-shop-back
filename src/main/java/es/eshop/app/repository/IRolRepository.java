package es.eshop.app.repository;

import es.eshop.app.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<Rol, Long> {
}
