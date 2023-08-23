package ua.ros.spring.hotel.model.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.*;
import ua.ros.spring.hotel.exeption.exceptions.BindingErrorsException;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.repository.BookingRepository;
import ua.ros.spring.hotel.model.service.BookingService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import com.querydsl.core.types.Predicate;
import ua.ros.spring.hotel.utils.validation.booking.BookingCreationValidator;

import static ua.ros.spring.hotel.exeption.exceptions.Message.BOOKING_CREATE_ERROR;

@Slf4j
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @PersistenceContext
    private EntityManager entityManager;
    private final BookingRepository bookingRepository;
    private final BookingCreationValidator bookingCreationValidator;

    @Value("${database.name}")
    private String databaseName;
    @Value("${database.booking.daysToPayBillBeforeDeleteBooking}")
    private String timeToPayBill;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingCreationValidator bookingCreationValidator) {
        this.bookingRepository = bookingRepository;
        this.bookingCreationValidator = bookingCreationValidator;
    }

    @Override
    public Boolean createBooking(Booking booking) {

        BindingResult errors = new BeanPropertyBindingResult(booking, "booking");
        bookingCreationValidator.validate(booking, errors);
        if (errors.hasErrors()){
            log.error("Cannot create Booking", errors);
            throw new BindingErrorsException(BOOKING_CREATE_ERROR, errors);
        }

        log.info("Create new Booking");
        entityManager.persist(booking);
        entityManager.flush();

        return createEventToCheckBillPaid(booking);
    }

    private boolean createEventToCheckBillPaid(Booking booking) {
        log.info("Create event to check paid for booking");
        String eventName = " IsBill_"+ booking.getId()+ "_Paid ";

        String createEvent = "CREATE EVENT " + eventName +
                "ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL :days_time_interval DAY " + //MINUTE
                "DO " +
                "DELETE FROM `" + databaseName + "`.`booking`" +
                "WHERE id = :id_value AND is_paid_for_reservation = 0 ";

        Query query = entityManager.createNativeQuery(createEvent)
                .setParameter("days_time_interval", timeToPayBill)
                // escaping attempt ("'"+timeToPayBill+"'") dosn`t throw any exception,
                // but also doesn`t allow to create event
                .setParameter("id_value", booking.getId());

        //receive operation result from DB by hibernate NOT SUPPORTED
        query.executeUpdate();
        return true;

        //Declarative management with DDL in hibernate NOT SUPPORTED
        //String eventName = "IsBill_"+booking.getId()+ "_Paid";
        /*return bookingRepository.createEventIsBillPaid(eventName,
                                                       timeToPayBill,
                                                       booking.getId() ) > 0;*/
    }

    @Transactional(readOnly=true)
    @Override
    @SuppressWarnings("unchecked")
    public Optional<Booking> findOne(Predicate predicate) {
        log.info("Find one Booking");
        Object reflectionObject = bookingRepository.findOne(predicate);
        return (Optional<Booking>) reflectionObject;
    }
    @Override
    @SuppressWarnings("unchecked")
    public Page<Booking> findAll(Predicate predicate, Pageable pageable) {
        log.info("Find all Bookings");
        Object reflectionObject = bookingRepository.findAll(predicate, pageable);
        return (Page<Booking>) reflectionObject;
    }
    @Override
    public Boolean updateBooking(Booking booking) {
        if(booking.getId() != null ) {
            log.info("Update Booking By Id -> " +booking.getId());
            entityManager.merge(booking);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteBooking(Booking booking) {
        log.info("Delete Booking By Id -> " +booking.getId());
        return bookingRepository.customDeleteById(booking.getId()) > 0;
    }

    @Transactional(readOnly=true)
    @Override
    public HashMap<Date, Date> findAllBookingsDatesRelatedToApartment(Long apartmentIdOfBookingDates) {
        //get all existed bookings related to apartment
        log.info("Find all Bookings Dates Related to Apartment");
        @NonNull ArrayList<Booking> bookings
                = bookingRepository.findAllBookingsRelatedToApartment(apartmentIdOfBookingDates).orElseThrow();
        //get all existed bookings dates (check-in/check-out) related to apartment
        return this.getBookingsDates(bookings);
    }
    private HashMap<Date, Date> getBookingsDates(ArrayList<Booking> bookings) {
        HashMap<Date, Date> bookingsDates = new HashMap<>();
        for(Booking booking : bookings){
            bookingsDates.put(booking.getCheckInDate(), booking.getCheckOutDate());
        }
        return bookingsDates;
    }
}
