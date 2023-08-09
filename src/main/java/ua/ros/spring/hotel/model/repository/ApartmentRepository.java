package ua.ros.spring.hotel.model.repository;


import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.springframework.stereotype.Repository;
import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.entity.QApartment;


/**
 * Apartment DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>, QuerydslPredicateExecutor<QApartment> {

    /**
     * Get a page of unique booked apartments
     * @param pageable conditions to perform paginating
     * @return page with apartments
     *
     */
    @Query(value = "SELECT SQL_CALC_FOUND_ROWS * FROM apartment "+
                    "INNER JOIN (SELECT DISTINCT apartment.`id` as inner_id FROM apartment " +
                                 "INNER JOIN booking ON booking.`apartment_id` = apartment.`id` " +
                                 "WHERE `booking`.`check_in_date` >= CURRENT_DATE() " +
                                       "OR `booking`.`check_out_date` >= CURRENT_DATE() " +
                    ") AS a2 " +
                    "ON apartment.`id` = a2.inner_id ",
             countQuery = " SELECT FOUND_ROWS() AS count ",
            nativeQuery = true)
    Page<Apartment> findAllApartmentsWhichAreBooked(Pageable pageable);

     /**
     * Get a page of unique free (not booked in the future), available (state is true) apartments
     * @param pageable conditions to perform paginating
     * @return page with apartments
     */
     @Query(value = "SELECT SQL_CALC_FOUND_ROWS * FROM apartment "+
             "WHERE apartment.`id` NOT IN (SELECT DISTINCT `apartment`.`id` as inner_id FROM apartment " +
             "INNER JOIN `booking` booking ON booking.`apartment_id` = `apartment`.`id` " +
             "WHERE `booking`.`check_in_date` >= CURRENT_DATE() OR `booking`.`check_out_date` >= CURRENT_DATE() " +
             ") AND apartment.state = 1 ",
             countQuery = " SELECT FOUND_ROWS() AS count ",
             nativeQuery = true)
    Page<Apartment>        findAllApartmentsWhichAreFree(Pageable pageable);

    Page<QApartment> findAll(Predicate predicate, Pageable pageable);


    /** Get objects using direct search algorithms from respective table by relevance.
     * <br> Include tag functionality to regulate relevancy and result set
     * @param pageable conditions to perform paginating
     * @param search_value user value
     * @return page with apartments
     */
    @Query(value = "SELECT SQL_CALC_FOUND_ROWS *, " +
            "MATCH(`title`, `description`, `address`, `apartment_class`) AGAINST(:search_value IN BOOLEAN MODE) AS REL " +
            "FROM apartment " +
            "WHERE MATCH(`title`, `description`, `address`, `apartment_class`) AGAINST(:search_value IN BOOLEAN MODE) " +
            "ORDER BY REL DESC ",
            countQuery = " SELECT FOUND_ROWS() AS count ",
            nativeQuery = true)
    Page<Apartment> searchApartments(Pageable pageable, String search_value);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM apartment WHERE id = ?1", nativeQuery = true)
    int costumDeleteById(Long id);
}
