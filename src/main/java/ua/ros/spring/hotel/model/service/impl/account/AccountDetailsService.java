package ua.ros.spring.hotel.model.service.impl.account;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Account;
import ua.ros.spring.hotel.model.entity.querydsl.QAccount;
import ua.ros.spring.hotel.model.repository.AccountRepository;

@Service
@Slf4j
@Transactional
public class AccountDetailsService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("ADS - username ->" + email);
        JPAQueryFactory queryFactory=new JPAQueryFactory(entityManager);
        Account account = accountRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found"));
        log.info("ADS - user ->" + account);
        return new AccountDetails(account);
    }


}
