package ua.ros.spring.hotel.controller.dto.apartment;

import lombok.Data;

@Data
public class ApartmentDTO {
    private Long id;

    private String title;
    private String description;
    private String imageURL;
    private String address;
    private String maxGuestsNumber;
    private String roomsNumber;
    private String apartmentClass;
    private Long price;
    //indicate state of apartment (false - not available/ true - available)
    private Boolean state;
}
