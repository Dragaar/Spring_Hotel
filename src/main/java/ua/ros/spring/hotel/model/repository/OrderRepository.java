package ua.ros.spring.hotel.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ua.ros.spring.hotel.model.entity.Order;
import ua.ros.spring.hotel.model.entity.QOrder;

import java.util.Optional;

/**
 * Order DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<QOrder> {
    @Query("SELECT Order FROM Order WHERE ?1 LIKE ?2")
    Optional<Order> findByField(String field, Object value);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM `order` WHERE id = ?1", nativeQuery = true)
    int customDeleteById(Long id);
}
