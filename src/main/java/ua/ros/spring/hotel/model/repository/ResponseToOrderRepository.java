package ua.ros.spring.hotel.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.entity.ResponseToOrder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;


/**
 * Response To Order DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface ResponseToOrderRepository extends JpaRepository<ResponseToOrder, Long> {

    @Query("SELECT ResponseToOrder FROM ResponseToOrder WHERE ?1 LIKE ?2")
    Optional<ResponseToOrder> findByField(String field, Object value);

    //Many-to-Many relationship

    /** Set(attach) apartment to response-to-order(RTO).
     * <br> Create new reference to the apartment in the RTO
     * @param con connection to database
     * @param rto response to order
     * @param apartment apartment to set to response
     * @return Boolean operation result
     */
    //boolean setApartmentToResponse(Connection con, ResponseToOrder rto, Apartment apartment);

    /** Get all attached to response-to-order(RTO) apartments by RTO id.
     *
     * @param con connection to database
     * @param id response to order id
     * @return ArrayList of attached apartments
     */
    //ArrayList<Apartment> getResponseApartments(Connection con, Long id);

    /** Delete all attached to response-to-order(RTO) apartments by RTO id.
     *  <br> Delete only references to apartments
     * @param con connection to database
     * @param id response to order id
     * @return Boolean operation result
     */
    //boolean deleteResponseApartments(Connection con, Long id);

}
