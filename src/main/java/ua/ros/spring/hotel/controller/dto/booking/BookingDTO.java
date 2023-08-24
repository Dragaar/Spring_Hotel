package ua.ros.spring.hotel.controller.dto.booking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class BookingDTO {

    private Long id;

    //@NotBlank
    private String guestsNumber;

    //@NotBlank
    private Date checkInDate;
    //@NotBlank
    private Date checkOutDate;

    private Timestamp reservationData;

    private Boolean isPaidForReservation = false;

    private Long accountId;
    //@NotNull
    private Long apartmentId;
}
