package ua.ros.spring.hotel.model.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.ros.spring.hotel.model.entity.Booking;

import java.sql.Date;
import java.util.HashMap;
import java.util.Optional;
import com.querydsl.core.types.Predicate;

/**
 * Booking Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface BookingService {

    /**
     * Create new booking
     * @param booking the booking to be added
     * @return operation result
     */
    Boolean                  createBooking(Booking booking);

    /**
     * Find booking by predicate
     * @param p conditions to receive booking
     * @return found booking
     */
    Optional<Booking>        findOne(Predicate p);

    /**
     * Find bookings by predicate
     * @param p conditions to receive bookings
     * @param pageable conditions to perform paginating
     * @return page with bookings
     */
    Page<Booking>            findAll(Predicate p, Pageable pageable);

    /**
     * Find all bookings
     * @param pageable conditions to perform paginating
     * @return page with bookings
     */
    Page<Booking>            findAll(Pageable pageable);
    /**
     * Update booking
     * @param booking booking to update
     * @return operation result
     */
    Boolean                  updateBooking(Booking booking);

    /**
     * Delete booking
     * @param bookingId booking id to delete
     * @return operation result
     */
    Boolean                  deleteBooking(Long bookingId);

    /**
     * Make payment for Booking
     * @param bookingId booking id
     * @return operation result
     */
    Boolean                  makePaymentForBooking(Long bookingId);

    /**
     * Get all booking dates of related apartment
     * @param apartmentIdOfBookingDates related apartment id
     * @return HashMap with check-in-date as key and check-out-date as value
     */
    HashMap<Date, Date>      findAllBookingsDatesRelatedToApartment(Long apartmentIdOfBookingDates);

}
