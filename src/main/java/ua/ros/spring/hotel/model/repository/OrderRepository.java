package ua.ros.spring.hotel.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.ros.spring.hotel.model.entity.Order;

import java.util.Optional;

/**
 * Order DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT Order FROM Order WHERE ?1 LIKE ?2")
    Optional<Order> findByField(String field, Object value);
}
