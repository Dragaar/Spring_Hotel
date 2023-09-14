package ua.ros.spring.hotel.controller.other.order;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.ros.spring.hotel.controller.dto.booking.BookingDTO;
import ua.ros.spring.hotel.controller.dto.order.OrderDTO;
import ua.ros.spring.hotel.model.entity.Booking;
import ua.ros.spring.hotel.model.entity.Order;
import ua.ros.spring.hotel.model.entity.QBooking;
import ua.ros.spring.hotel.model.entity.QOrder;
import ua.ros.spring.hotel.model.service.OrderService;

import static ua.ros.spring.hotel.controller.constant.ControllerConstant.BOOKINGS_HTML;
import static ua.ros.spring.hotel.controller.constant.ControllerConstant.ORDERS_HTML;

@Slf4j
@Controller
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String userOrdersPage(Model model,
                                 @PageableDefault(sort = {"id"}) Pageable pageable,
                                 @AuthenticationPrincipal(expression = "id") @NonNull Long accountId)
    {
        Page<Order> orderPage;

        log.info("GET Orders. ");
        orderPage = orderService.findAll(
                QOrder.order.account.id.eq(accountId),
                pageable);

        Page<OrderDTO> orderDTOPage =
                orderPage.map(order -> modelMapper.map(order, OrderDTO.class));

        log.info("Orders -> " + orderDTOPage.getContent()) ;

        model.addAttribute("page", orderDTOPage);
        return ORDERS_HTML;
    }
}
