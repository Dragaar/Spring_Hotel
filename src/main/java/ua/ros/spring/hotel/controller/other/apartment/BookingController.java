package ua.ros.spring.hotel.controller.other.apartment;

import com.querydsl.core.types.Predicate;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ros.spring.hotel.controller.dto.apartment.ApartmentDTO;
import ua.ros.spring.hotel.controller.dto.booking.BookingDTO;
import ua.ros.spring.hotel.exeption.exceptions.SecurityException;
import ua.ros.spring.hotel.model.entity.AccountRole;
import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.entity.QBooking;
import ua.ros.spring.hotel.model.service.ApartmentService;
import ua.ros.spring.hotel.model.service.BookingService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static ua.ros.spring.hotel.controller.constant.ControllerConstant.*;


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

        log.info("Bookings -> " + bookingPage.getContent()) ;

        model.addAttribute("page", bookingPage);
        return BOOKINGS_HTML;
    }
    @GetMapping("/all")
    public String bookingsPage(Model model,
                               @PageableDefault(sort = {"id"}) Pageable pageable,
                               @AuthenticationPrincipal(expression = "authorities") @NonNull Collection<AccountRole> accountRoles
    ) {
        Page<Booking> bookingPage;

        log.info("GET Bookings. ");
        if(accountRoles.contains(AccountRole.ROLE_MANAGER)) {
            bookingPage = bookingService.findAll(pageable);
            model.addAttribute("page", bookingPage);
        } else {
            log.error("Try to access page without permission. Current granted auth. -> " + accountRoles);
            throw new SecurityException("You dont have access to All users Bookings page!!", new RuntimeException());
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

