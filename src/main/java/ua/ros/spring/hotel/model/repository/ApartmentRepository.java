package ua.ros.spring.hotel.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.ros.spring.hotel.model.entity.Account;
import ua.ros.spring.hotel.model.entity.Apartment;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Apartment DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query("SELECT Apartment FROM Apartment WHERE ?1 LIKE ?2")
    Optional<Apartment> findByField(String field, Object value);
    /** Get a list of unique booked apartments and apply to them query-builder part (Sorting, limits, etc.)
     *
     * @param con connection to database
     * @param secondQueryPart query builder string part
     * @param fields fields for insertion in query builder string part statement
     * @return ArrayList  result array
     */
    //ArrayList<Apartment>        getUniqueApartmentsWhichAreBookedWithDynamicQuery(Connection con, String secondQueryPart, Object... fields);

    /** Get a list of unique free (not booked in the future), available (state is true) apartments
     *
     * @param con connection to database
     * @param secondQueryPart query builder string part
     * @param fields fields for insertion in query builder string part statement
     * @return ArrayList  result array
     */
    //ArrayList<Apartment>        getUniqueApartmentsWhichAreFree(Connection con, String secondQueryPart, Object... fields);

    /** Get objects using direct search algorithms from respective table by relevance.
     * <br> Include tag functionality to regulate relevancy and result set
     * @param con connection to database
     * @param value user value
     * @return ArrayList<T> result array
     */
    //ArrayList<Apartment> searchApartments(Connection con, String value, int start, int total);
}
