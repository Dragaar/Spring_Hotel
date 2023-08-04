package ua.ros.spring.hotel.model.service;


import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.entity.Order;
import ua.ros.spring.hotel.model.entity.ResponseToOrder;

import java.util.ArrayList;

/**
 * Response To Order Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface ResponseToOrderService {

    /**
     * Create new response-to-order
     * @param order order to which to respond
     * @param responseToOrder the response-to-order to be added
     * @param apartments apartments attached to response
     * @return operation result
     */
    Boolean                            createResponseToOrder(Order order, ResponseToOrder responseToOrder, ArrayList<Apartment> apartments);

    /**
     * Find response-to-order by field
     * @param field field name
     * @param value unique field value
     * @return found response-to-order
     */
    ResponseToOrder                    findResponseToOrderByField(String field, Object value);

    /**
     * Update response-to-order
     * @param responseToOrder response-to-order to update
     * @return operation result
     */
    Boolean                            updateResponseToOrder(ResponseToOrder responseToOrder);

    //-----------------------Many to Many---------------------------\\

    /**
     * Find all apartments attached to response-to-order
     * @param responseToOrder response-to-order by which search
     * @return found response-to-order
     */
    ArrayList<Apartment>     findAllResponseApartments(ResponseToOrder responseToOrder);

}
