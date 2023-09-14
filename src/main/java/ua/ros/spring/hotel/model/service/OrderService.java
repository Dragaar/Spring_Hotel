package ua.ros.spring.hotel.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.ros.spring.hotel.model.entity.Order;

import java.util.Optional;
import com.querydsl.core.types.Predicate;

/**
 * Order Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface OrderService {

    /**
     * Create new order
     * @param order the order to be added
     * @return operation result
     */
    Boolean                 createOrder(Order order);

    /**
     * Find order by predicate
     * @param p conditions to receive order
     * @return found order
     */
    Optional<Order>       findOne(Predicate p);

    /**
     * Find orders by predicate
     * @param p conditions to receive orders
     * @param pageable conditions to perform paginating
     * @return page with orders
     */
    Page<Order>           findAll(Predicate p, Pageable pageable);

    /**
     * Update order
     * @param order order to update
     * @return operation result
     */
    Boolean                  updateOrder(Order order);
    /**
     * Delete order
     * @param order order to delete
     * @return operation result
     */
    Boolean                  deleteOrder(Order order);

}
