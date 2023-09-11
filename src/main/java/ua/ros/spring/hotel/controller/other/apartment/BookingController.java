package ua.ros.spring.hotel.controller.other.apartment;

import jakarta.validation.Valid;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ros.spring.hotel.controller.dto.booking.BookingDTO;
import ua.ros.spring.hotel.exeption.exceptions.SecurityException;
import ua.ros.spring.hotel.model.entity.AccountRole;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.entity.QBooking;
import ua.ros.spring.hotel.model.service.ApartmentService;
import ua.ros.spring.hotel.model.service.BookingService;

import java.util.Collection;

import static ua.ros.spring.hotel.controller.constant.ControllerConstant.*;
import static ua.ros.spring.hotel.exeption.exceptions.Message.DONT_HAVE_PERMISSION;


@Slf4j
@Controller
@RequestMapping("/bookings")
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

    @GetMapping
    public String userBookingsPage(Model model,
                               @PageableDefault(sort = {"id"}) Pageable pageable,
                               @AuthenticationPrincipal(expression = "id") @NonNull Long accountId
    ) {
        Page<Booking> bookingPage;

        log.info("GET Bookings. ");
        bookingPage = bookingService.findAll(
                QBooking.booking.account.id.eq(accountId),
                pageable);

        Page<BookingDTO> bookingDTOPage =
                bookingPage.map(booking -> modelMapper.map(booking, BookingDTO.class));

        log.info("Bookings -> " + bookingDTOPage.getContent()) ;

        model.addAttribute("page", bookingDTOPage);
        return BOOKINGS_HTML;
    }
    @GetMapping("/all")
    public String bookingsPage(Model model,
                               @PageableDefault(sort = {"id"}) Pageable pageable,
                               @AuthenticationPrincipal(expression = "authorities") @NonNull Collection<AccountRole> accountRoles
    ) {
        Page<Booking> bookingPage;

        log.info("GET All Bookings. ");
        if(accountRoles.contains(AccountRole.ROLE_MANAGER)) {
            bookingPage = bookingService.findAll(pageable);
            Page<BookingDTO> bookingDTOPage =
                    bookingPage.map(booking -> modelMapper.map(booking, BookingDTO.class));

            model.addAttribute("page", bookingDTOPage);
        } else {
            log.error("Try to access page without permission. Current granted auth. -> " + accountRoles);
            throw new SecurityException(DONT_HAVE_PERMISSION, new RuntimeException());
        }
        return BOOKINGS_HTML;
    }

    @PostMapping("/makePaymentForBooking")
    public String makePaymentForBooking(@RequestParam(name = "bookingId") @NonNull Long bookingId
    ) {
        log.info("POST Make payment for booking");
        bookingService.makePaymentForBooking(bookingId);

        return "redirect:/bookings";
    }

    //TODO Добавити валідацію ролі
    @PostMapping("/deleteBooking")
    public String deleteBooking(@RequestParam(name = "bookingId") @NonNull Long bookingId
    ) {
        log.info("POST Delete booking");
        bookingService.deleteBooking(bookingId);

        return "redirect:/bookings";
    }

}

