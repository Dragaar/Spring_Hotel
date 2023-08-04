package ua.ros.spring.hotel.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.ros.spring.hotel.model.entity.Order;

import java.util.ArrayList;

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
    Boolean                  createOrder(Order order);

    /**
     * Find order by field
     * @param field field name
     * @param value unique field value
     * @return found order
     */
    Order                    findOrderByField(String field, Object value);

    /**
     * Find few orders by total count and from set start id
     * @param pageable
     * @return found orders
     */
    Page<Order>       findOrders(Pageable pageable);

    /**
     * Find few orders using second query part conditions and by fields for them
     * @param secondQueryPart QueryBuilder result string
     * @param fields fields to insert in second query part statement
     * @return found orders
     */
    ArrayList<Order>         findFewOrdersAndSort(String secondQueryPart, Object... fields);

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
