package ua.ros.spring.hotel.controller.security;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ros.spring.hotel.controller.dto.account.AccountRegistrationDTO;
import ua.ros.spring.hotel.model.entity.Account;
import ua.ros.spring.hotel.model.service.impl.account.RegistrationService;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    //private final AccountRegistrationDTOValidator accountRegistrationDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(RegistrationService registrationService,
                          //AccountRegistrationDTOValidator accountRegistrationDTOValidator,
                          ModelMapper modelMapper) {
        this.registrationService = registrationService;
        //this.accountRegistrationDTOValidator = accountRegistrationDTOValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("account") AccountRegistrationDTO account) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("account") @Valid AccountRegistrationDTO account,
                               BindingResult bindingResult) {
        //accountRegistrationDTOValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        registrationService.register(modelMapper.map(account, Account.class));
        log.info(String.format("%s registered", account.getEmail()));
        return "redirect:/auth/login";
    }
}