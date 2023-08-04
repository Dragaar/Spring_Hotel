package ua.ros.spring.hotel.model.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.repository.BookingRepository;
import ua.ros.spring.hotel.model.service.BookingService;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;


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

        //get all existed bookings related to apartment
        @NonNull final ArrayList<Booking> bookings
            = bookingRepository.findAllBookingsRelatedToApartment(relatedApartmentId).orElseThrow();
        //get all existed bookings dates (check-in/check-out) related to apartment
        @NonNull final HashMap<Date, Date> bookingsDates
            = this.getBookingsDates(bookings);

            //validation.validateBooking(booking, bookingsDates);

        entityManager.persist(booking);

        String eventName = "IsBill_"+booking.getId()+ "_Paid";
        return bookingRepository.createEventIsBillPaid(eventName, databaseName, booking.getId());

        /*if     (bookingDAO.insert(connection, booking))
        return bookingDAO.createEventIsBillPaid(connection, booking.getId());
        else return false;*/
    }
    
    @Override
    public ArrayList<Booking> findFewBookingAndSort(String secondQueryPart, Object... fields) {
      /*  Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Booking> result = (ArrayList<Booking>) TransactionManager.execute(connection,
                ()-> {
                ArrayList<Booking> r = bookingDAO.getWithDynamicQuery(connection, secondQueryPart, fields);
                rowsNumber = bookingDAO.countRowsInLastQuery(connection);
                return r;
                }
        );
        return result;*/
        throw new UnsupportedOperationException();
    }
    @Override
    public Boolean updateBooking(Booking booking) {
        if(booking.getId() != null ) {
            entityManager.merge(booking);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteBooking(Booking booking) {
        bookingRepository.delete(booking);
        return true;
    }

    @Override
    public HashMap<Date, Date> getBookingsDatesFromDB(Long apartmentIdOfBookingDates) {
       /* Date currentDate = Date.valueOf(LocalDate.now());

        ArrayList<Booking> bookings = this.findFewBookingAndSort(
                getQueryForActualBookingsByAccountId(),
                apartmentIdOfBookingDates,
                currentDate,
                currentDate
        );

        return getBookingsDates(bookings);*/
        throw new UnsupportedOperationException();
    }
    private HashMap<Date, Date> getBookingsDates(ArrayList<Booking> bookings) {
        HashMap<Date, Date> bookingsDates = new HashMap<>();
        for(Booking booking : bookings){
            bookingsDates.put(booking.getCheckInDate(), booking.getCheckOutDate());
        }
        return bookingsDates;
    }
}
