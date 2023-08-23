package ua.ros.spring.hotel.controller.constant;

/**
 * Controller Constant class. It contains views paths, security config paths and controllers names
 *
 * @author Rostyslav Ivanyshyn.
 */
public class ControllerConstant {


    //---------------- Html Directories -------------------\\
    public static final String AUTHENTICATION_DIR = "/auth";
    public static final String APARTMENT_DIR = "/pages/apartment";
    public static final String BOOKING_DIR= "/pages/booking";
    public static final String ORDER_DIR= "/pages/order";
    public static final String RTO_DIR= "/pages/rto";
    public static final String PROFILE_DIR= "/pages/profile";

    //---------------- Html pages -------------------\\
    public static final String INDEX_HTML = "/common/index.html";
    public static final String ERROR_HTML = "/common/error.html";

    public static final String LOGIN_HTML = AUTHENTICATION_DIR+"/login.html";
    public static final String REGISTRATION_HTML= AUTHENTICATION_DIR+"/registration.html";
    public static final String APARTMENTS_HTML = APARTMENT_DIR+"/apartments.html";
    public static final String APARTMENT_DETAILS_HTML= APARTMENT_DIR+"/apartment.html";

    public static final String ALL_BOOKINGS_HTML = BOOKING_DIR+"/allBookings.html";
    public static final String BOOKINGS_HTML = BOOKING_DIR+"/bookings.html";
    public static final String NEW_BOOKING_HTML = BOOKING_DIR+"/newBooking.html";

    public static final String ALL_ORDERS_HTML = ORDER_DIR+"/allOrders.html";
    public static final String ORDERS_HTML = ORDER_DIR+"/orders.html";
    public static final String NEW_ORDER_HTML = ORDER_DIR+"/newOrder.html";

    public static final String RESPONSE_TO_ORDER_HTML = RTO_DIR+"/responseToOrder.html";
    public static final String NEW_RESPONSE_TO_ORDER_HTML = RTO_DIR+"/newResponseToOrder.html";

    public static final String ACCOUNT_DETAILS_HTML = PROFILE_DIR+"/account.html";
}
