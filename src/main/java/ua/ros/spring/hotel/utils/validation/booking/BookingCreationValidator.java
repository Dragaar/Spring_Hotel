package ua.ros.spring.hotel.utils.validation.booking;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.entity.QApartment;
import ua.ros.spring.hotel.model.service.ApartmentService;
import ua.ros.spring.hotel.model.service.BookingService;
import ua.ros.spring.hotel.utils.validation.GeneralValidator;

import java.sql.Date;
import java.util.HashMap;
import java.util.Optional;

import static ua.ros.spring.hotel.exeption.exceptions.Message.*;

@Slf4j
@Component
public class BookingCreationValidator implements Validator {
    private final BookingService bookingService;
    private final ApartmentService apartmentService;
    private final GeneralValidator gV;

    public BookingCreationValidator(@Lazy BookingService bookingService, ApartmentService apartmentService, GeneralValidator gV) {
        this.bookingService = bookingService;
        this.apartmentService = apartmentService;
        this.gV = gV;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Booking.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        log.info("Validation (Booking) begin");
        Booking booking = (Booking) target;
        validateBooking(booking, errors);
        validateAssignedApartment(booking, errors);
        log.info("Validation (Booking) end");
    }

    public void validateBooking(final Booking booking, Errors errors) {

        String guestsNumber = booking.getGuestsNumber();

        if (!gV.isValidNumber(guestsNumber)) {
            log.error("Wrong input Guests Number");
            errors.rejectValue("guestsNumber", "", INCORRECT_GUESTS_NUMBER);
        }
    }

    private void validateAssignedApartment(Booking booking, Errors errors) {
        @NonNull final Long apartmentId
                = booking.getApartment().getId();

        Optional<Apartment> apartment
                = apartmentService.findOne(QApartment.apartment.id.eq(apartmentId));

        if(apartment.isEmpty()) {
            log.error("Apartment not exist");
            errors.rejectValue("apartment.id", "", APARTMENT_ISNT_EXIST);
        } else if(apartment.get().getState() == false) {
            log.error("Apartment not available");
            errors.rejectValue("apartment.id", "", APARTMENT_ISNT_AVAILABLE);
        } else {
            //get related apartment
            @NonNull Long relatedApartmentId = apartment.get().getId();

            //get all existed bookings dates (check-in/check-out) related to apartment
            @NonNull HashMap<Date, Date> existingBookingsDates
                    = bookingService.findAllBookingsDatesRelatedToApartment(relatedApartmentId);

            validateDates(booking, existingBookingsDates, errors);
        }
    }

    private void validateDates(Booking booking, HashMap<Date, Date> existingBookingsDates,  Errors errors) {

        Date checkInDate = booking.getCheckInDate();
        Date checkOutDate = booking.getCheckOutDate();

        if (!gV.isValidDate(checkInDate.toString())
                ||!gV.isValidDate(checkOutDate.toString())
                ||!gV.isDataInFutureTime(checkInDate)
        ) {
            log.error("Wrong input Data");
            errors.rejectValue("checkInDate", "", INCORRECT_BOOKING_DATE);
        }
        if(existingBookingsDates != null){

            existingBookingsDates.forEach(
                    (fCheckInDate, fCheckOutDate)-> {
                        if(!gV.comparisonDataValidator(fCheckInDate, fCheckOutDate,
                                checkInDate, checkOutDate))
                        {   log.error("Booking on those dates already exist");
                            errors.rejectValue("checkInDate", "", BOOKING_ALREADY_EXIST_ON_THIS_DATE); }
                    }
            );
        }
    }


}
