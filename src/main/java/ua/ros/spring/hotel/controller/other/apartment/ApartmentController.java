package ua.ros.spring.hotel.controller.other.apartment;

import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ros.spring.hotel.model.entity.Apartment;
import ua.ros.spring.hotel.model.service.ApartmentService;
import ua.ros.spring.hotel.model.service.BookingService;
import ua.ros.spring.hotel.utils.DateUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static ua.ros.spring.hotel.controller.constant.ControllerConstant.APARTMENTS_HTML;
import static ua.ros.spring.hotel.controller.constant.ControllerConstant.APARTMENT_DETAILS_HTML;

@Slf4j
@Controller
@RequestMapping("/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final BookingService bookingService;
    //private final ApartmentDTOValidator apartmentDTOValidator;
    private final DateUtil dateUtil;
    private final ModelMapper modelMapper;

    public ApartmentController(ApartmentService apartmentService, BookingService bookingService, ModelMapper modelMapper, DateUtil dateUtil) {
        this.apartmentService = apartmentService;
        this.bookingService = bookingService;
        this.modelMapper = modelMapper;
        this.dateUtil = dateUtil;
    }

    @GetMapping
    public String apartmentsPage(Model model,
                                 @QuerydslPredicate(root = Apartment.class) Predicate predicate,
                                 @PageableDefault(sort = {"title"}) Pageable pageable,
                                 @RequestParam(name = "searchValue", required = false) @Size(max = 50) String searchValue
                                 ) {
        Page<Apartment> apartmentPage;

        if(isNotBlank(searchValue)){
            log.info("GET Apartments. "+"Performing search by criteria -> " + searchValue);
            log.info("Sort Order -> " + pageable.getSort());
            apartmentPage = apartmentService.searchApartments(pageable, searchValue);
        } else {
            log.info("GET Apartments. "+"Searching not performing. STD behavior");
            apartmentPage = apartmentService.findAll(predicate, pageable);
        }

        model.addAttribute("searchValue", searchValue);
        //model.addAttribute("controllerName", "apartments");
        model.addAttribute("page", apartmentPage);
       return APARTMENTS_HTML;
    }
    //TODO Добавити зберігання Sort в сесії

    private boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
    private boolean isNotBlank(String value) {
        return !isBlank(value);
    }


}
