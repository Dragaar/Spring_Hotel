package ua.ros.spring.hotel.model.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.entity.QBooking;

import java.util.ArrayList;
import java.util.Optional;

import static ua.ros.spring.hotel.model.constant.Query.DB_NAME_AND_BOOKING_TABLE_NAME;

/**
 * Booking DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, QuerydslPredicateExecutor<QBooking> {

    @Query("SELECT b FROM Booking b " +
            "WHERE b.apartment.id = ?1 " +
            "AND (b.checkInDate >= CURRENT_DATE " +
            "OR b.checkOutDate >= CURRENT_DATE)")
    Optional<ArrayList<Booking>> findAllBookingsRelatedToApartment(Long apartmentId);

    //NOT SUPPORTED WITH HIBERNATE
   /* @Modifying
    @Query(value="CREATE EVENT :event_name " +
            "ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL :days_time_interval  MINUTE " +
            "DO " +
            "DELETE FROM " + DB_NAME_AND_BOOKING_TABLE_NAME +
            "WHERE id = :id_value AND is_paid_for_reservation = 0 ",
            nativeQuery=true)
    int createEventIsBillPaid(@Param("event_name") String eventName,
                              @Param("days_time_interval") String daysTimeInterval,
                              @Param("id_value")  Long id);*/

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM booking WHERE id = ?1", nativeQuery = true)
    int customDeleteById(Long id);
}
