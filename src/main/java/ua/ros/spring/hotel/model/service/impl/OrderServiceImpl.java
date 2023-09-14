package ua.ros.spring.hotel.model.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Order;
import ua.ros.spring.hotel.model.repository.OrderRepository;
import ua.ros.spring.hotel.model.repository.ResponseToOrderRepository;
import ua.ros.spring.hotel.model.service.OrderService;

import java.util.Optional;

import com.querydsl.core.types.Predicate;
@Slf4j
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
            log.info("Create new Order");
            entityManager.persist(order);
            return true;
        }
        return false;
    }

    @Transactional(readOnly=true)
    @Override
    @SuppressWarnings("unchecked")
    public Optional<Order> findOne(Predicate predicate) {
        log.info("Find one Order");
        Object reflectionObject = orderRepository.findOne(predicate);
        return (Optional<Order>) reflectionObject;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Order> findAll(Predicate predicate, Pageable pageable) {
        log.info("Find all Orders");
        Object reflectionObject = orderRepository.findAll(predicate, pageable);
        return (Page<Order>) reflectionObject;
    }

    @Override
    public Boolean updateOrder(Order order) {
        if(order.getId() != null ) {
            log.info("Update Order By Id -> " +order.getId());
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
            log.info("Delete RTO Related to Order");
            // TODO
            // видалення many to many
            // responseToOrderDAO.deleteResponseApartments(connection, order.getResponseToOrder().getId());
            rtoRepository.delete(order.getResponseToOrder());
        }
        log.info("Delete Order By Id -> " +order.getId());
        return orderRepository.customDeleteById(order.getId()) > 0;
    }

}
