package ua.ros.spring.hotel.controller.other.apartment;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.ros.spring.hotel.controller.dto.booking.BookingDTO;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.service.ApartmentService;
import ua.ros.spring.hotel.model.service.BookingService;


@Slf4j
@Controller
@RequestMapping("/booking")
public class BookingController {

    private final ApartmentService apartmentService;
    private final BookingService bookingService;

    private final ModelMapper modelMapper;

    public BookingController(ApartmentService apartmentService, BookingService bookingService, ModelMapper modelMapper) {
        this.apartmentService = apartmentService;
        this.bookingService = bookingService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/createBooking")
    public String createBooking(
            @Valid BookingDTO bookingDTO,
            @AuthenticationPrincipal(expression = "id") @NonNull Long accountId
    ){
        log.info("POST Create new Booking. By Apartment Id " + bookingDTO.getApartmentId());
        bookingDTO.setAccountId(accountId);

        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        //Validation inside service
        bookingService.createBooking(booking);
        return "redirect:/apartments";
    }
}

