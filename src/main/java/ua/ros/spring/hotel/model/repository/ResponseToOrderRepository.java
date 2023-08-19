package ua.ros.spring.hotel.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ua.ros.spring.hotel.model.entity.QResponseToOrder;
import ua.ros.spring.hotel.model.entity.ResponseToOrder;

import java.util.Optional;


/**
 * Response To Order DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
@Repository
public interface ResponseToOrderRepository extends JpaRepository<ResponseToOrder, Long>, QuerydslPredicateExecutor<QResponseToOrder> {

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
     * @param id response to order id
     * @return ArrayList of attached apartments
     */
    //ArrayList<Apartment> getResponseApartments(Connection con, Long id);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM response_to_order WHERE id = ?1", nativeQuery = true)
    int customDeleteById(Long id);

    /** Delete all attached to response-to-order(RTO) apartments by RTO id.
     *  <br> Delete only references to apartments
     * @param con connection to database
     * @param id response to order id
     * @return Boolean operation result
     */
    //boolean deleteResponseApartments(Connection con, Long id);


}
