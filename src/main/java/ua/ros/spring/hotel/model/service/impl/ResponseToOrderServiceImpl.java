package ua.ros.spring.hotel.model.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.entity.Order;
import ua.ros.spring.hotel.model.entity.ResponseToOrder;
import ua.ros.spring.hotel.model.repository.ResponseToOrderRepository;
import ua.ros.spring.hotel.model.service.ResponseToOrderService;

import java.sql.Connection;
import java.util.ArrayList;


@Service
@Transactional
public class ResponseToOrderServiceImpl implements ResponseToOrderService {
    @PersistenceContext
    private EntityManager entityManager;
    private final ResponseToOrderRepository responseToOrderRepository;

    public ResponseToOrderServiceImpl(ResponseToOrderRepository responseToOrderRepository) {
        this.responseToOrderRepository = responseToOrderRepository;
    }

    /** Attach response-to-order in order and submit apartment to response-to-order
     * @param order order in which attach response
     * @param responseToOrder response-to-order in which submit apartment
     * @param apartments submittable apartments
     * @return result of operation
     */
    @Override
    public Boolean createResponseToOrder(Order order, ResponseToOrder responseToOrder, ArrayList<Apartment> apartments) {
       /* Connection connection = dbManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> {
                    responseToOrderDAO.insert(connection, responseToOrder);

                    //Update foreign key
                    order.setResponseToOrder(responseToOrder);
                    orderDAO.update(connection, order);

                    //Create many-to-many relationship
                    for (Apartment apartment : apartments) {
                        responseToOrderDAO.setApartmentToResponse(connection, responseToOrder, apartment);
                    }
                    return true;
                }
        );*/
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseToOrder findResponseToOrderByField(String field, Object value) {
        return responseToOrderRepository.findByField(field, value).orElseThrow();
    }

    @Override
    public Boolean updateResponseToOrder(ResponseToOrder responseToOrder) {
        if(responseToOrder.getId() != null ) {
            entityManager.merge(responseToOrder);
            return true;
        }
        return false;
    }

    /**
     * Get submitted apartments to response-to-order
     * @param responseToOrder response-to-order in which submitted apartments
     * @return submitted apartments
     */
    @Override
    public ArrayList<Apartment> findAllResponseApartments(ResponseToOrder responseToOrder) {
        //return responseToOrder.getApartments();
        throw new UnsupportedOperationException();
       /* Connection connection = dbManager.getConnection();

        @SuppressWarnings("unchecked")
        ArrayList<Apartment> result = (ArrayList<Apartment>) TransactionManager.execute(connection,
                ()-> responseToOrderDAO.getResponseApartments(connection, responseToOrder.getId())
        );
        return result;*/
    }

}
