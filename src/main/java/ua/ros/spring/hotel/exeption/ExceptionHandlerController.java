package ua.ros.spring.hotel.exeption;

import com.querydsl.core.types.Predicate;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ua.ros.spring.hotel.controller.dto.account.AccountRegistrationDTO;
import ua.ros.spring.hotel.exeption.exceptions.BindingErrorsException;
import ua.ros.spring.hotel.model.entity.Apartment;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.ros.spring.hotel.controller.constant.ControllerConstant.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    //==================================================================
    // HANDLE EXCEPTIONS
    //==================================================================

    @ExceptionHandler(BindingErrorsException.class)
    public String handleExceptions(Model model, BindingErrorsException exception) {

        //ITERATE THROUGH ERRORS
        List<ObjectError> objectErrors = exception.getBindingResult().getAllErrors();

        @AllArgsConstructor
        class ErrorData{
            //public String fieldName;
            public String errorMessage;
        }
        List<ErrorData> errorData = new ArrayList<>();
        errorData.add(new ErrorData(exception.getMessage()));

        for (ObjectError objectError : objectErrors) {
            //GET ERROR
            FieldError fieldError = (FieldError) objectError;

            //String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            //STORE ERROR
            errorData.add(new ErrorData(errorMessage));
        }
        model.addAttribute("errorData", errorData);

        return ERROR_HTML;
    }

}
