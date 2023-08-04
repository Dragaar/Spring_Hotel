package ua.ros.spring.hotel.model.service.impl.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Account;
import ua.ros.spring.hotel.model.entity.AccountRole;
import ua.ros.spring.hotel.model.service.AccountService;

@Service
@Transactional
public class RegistrationService {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(AccountRole.ROLE_USER);
        accountService.createAccount(account);
    }
}