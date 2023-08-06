package ua.ros.spring.hotel.controller.dto.account;

import lombok.Data;
import ua.ros.spring.hotel.model.entity.AccountRole;

@Data
public class AccountDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private AccountRole role;
    private Boolean state;
}