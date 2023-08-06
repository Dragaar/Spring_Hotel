package ua.ros.spring.hotel.controller.dto.account;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordUpdateDTO {
    private int id;
    private String oldPassword;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$", message = "invalid.password")
    private String newPassword;

    private String confirmPassword;
}