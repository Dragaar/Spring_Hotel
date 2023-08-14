package ua.ros.spring.hotel.model.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.repository.BookingRepository;
import ua.ros.spring.hotel.model.service.BookingService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import com.querydsl.core.types.Predicate;

@Slf4j
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @PersistenceContext
    private EntityManager entityManager;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Value("${database.name}")
    private String databaseName;

    @Override
    public Boolean createBooking(Booking booking) {
        
        //get related apartment
        @NonNull Long relatedApartmentId = booking.getApartment().getId();

        //get all existed bookings dates (check-in/check-out) related to apartment
        @NonNull HashMap<Date, Date> bookingsDates
            = findAllBookingsDatesRelatedToApartment(relatedApartmentId);

        //validation.validateBooking(booking, bookingsDates);
        log.info("Create new Booking");
        entityManager.persist(booking);

        String eventName = "IsBill_"+booking.getId()+ "_Paid";
        return bookingRepository.createEventIsBillPaid(eventName, databaseName, booking.getId());

        /*if     (bookingDAO.insert(connection, booking))
        return bookingDAO.createEventIsBillPaid(connection, booking.getId());
        else return false;*/
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
