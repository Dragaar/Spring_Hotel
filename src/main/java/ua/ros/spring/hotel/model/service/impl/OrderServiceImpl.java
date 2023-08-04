package ua.ros.spring.hotel.model.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Order;
import ua.ros.spring.hotel.model.repository.OrderRepository;
import ua.ros.spring.hotel.model.repository.ResponseToOrderRepository;
import ua.ros.spring.hotel.model.service.OrderService;

import java.util.ArrayList;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;
    private final OrderRepository orderRepository;
    ResponseToOrderRepository rtoRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ResponseToOrderRepository rtoRepository) {
        this.orderRepository = orderRepository;
        this.rtoRepository = rtoRepository;
    }

    @Override
    public Boolean createOrder(Order order) {
        if( order.getId() == null ) {
            entityManager.persist(order);
            return true;
        }
        return false;
    }

    @Override
    public Order findOrderByField(String field, Object value) {
        return orderRepository.findByField(field, value).orElseThrow();
    }

    @Override
    public Page<Order> findOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }


    @Override
    public ArrayList<Order> findFewOrdersAndSort(String secondQueryPart, Object... fields) {
       /* Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Order> result = (ArrayList<Order>) TransactionManager.execute(connection,
                ()-> {
                ArrayList<Order> r = orderDAO.getWithDynamicQuery(connection, secondQueryPart, fields);
                rowsNumber = orderDAO.countRowsInLastQuery(connection);
                return r;
                }
        );
        return result;*/
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean updateOrder(Order order) {
        if(order.getId() != null ) {
            entityManager.merge(order);
            return true;
        }
        return false;
    }

    /**
     * Delete order, response-to-order and all apartments submitted to response-to-order
     * @param order order to delete
     * @return Boolean result of operation
     */
    @Override
    public Boolean deleteOrder(Order order) {
        if(order.getResponseToOrder() != null) {
            // TODO
            // видалення many to many
            // responseToOrderDAO.deleteResponseApartments(connection, order.getResponseToOrder().getId());
            rtoRepository.delete(order.getResponseToOrder());
        }
        orderRepository.delete(order);
        return true;
    }

}
