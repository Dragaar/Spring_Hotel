package ua.ros.spring.hotel.model.repository;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.entity.Booking;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Booking DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "WHERE b.apartment.id = ?1 " +
            "AND b.checkInDate >= CURRENT_DATE " +
            "AND b.checkOutDate >= CURRENT_DATE")
    Optional<ArrayList<Booking>> findAllBookingsRelatedToApartment(Long apartmentId);
    /** Create DB event:
     * <br> After the specified period of time,
     * <br> If bill for booking isn`t paid -> delete booking
     *
     * @param id id of the booking being checked
     * @return Boolean operation result
     */
    @Modifying
    @Query(value="CREATE EVENT :name " +
            "ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 1 MINUTE " +
            "DO " +
            "DELETE FROM :db_name.`booking` " +
            "WHERE id = :id_value AND is_paid_for_reservation = 0",
            nativeQuery=true)
    Boolean createEventIsBillPaid(@Param("name") String eventName,
                                  @Param("db_name") String databaseName,
                                  @Param("id_value")  Long id);


}
