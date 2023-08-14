package ua.ros.spring.hotel.model.service.impl.account;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Account;
import ua.ros.spring.hotel.model.repository.AccountRepository;
import ua.ros.spring.hotel.model.service.AccountService;


import java.util.Optional;
import com.querydsl.core.types.Predicate;

@Slf4j
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @PersistenceContext
    private EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean createAccount(Account account) {
        if( account.getId() == null ) {
            log.info("Create new Account");
            entityManager.persist(account);
            return true;
        }
        return false;
    }

    @Transactional(readOnly=true)
    @Override
    public Boolean isAccountExist(Account account) {
        log.info("Check is Account exist");
        Account tempAccount = accountRepository.findById(account.getId()).orElseThrow();
        return tempAccount.equals(account);
    }

    @Transactional(readOnly=true)
    @Override
    @SuppressWarnings("unchecked")
    public Optional<Account> findOne(Predicate predicate) {
        log.info("Find one Account");
        Object reflectionObject = accountRepository.findOne(predicate);
        return (Optional<Account>) reflectionObject;
    }

    @Transactional(readOnly=true)
    @Override
    @SuppressWarnings("unchecked")
    public Page<Account> findAll(Predicate predicate, Pageable pageable) {
        log.info("Find all Accounts");
        Object reflectionObject = accountRepository.findAll(predicate, pageable);
        return (Page<Account>) reflectionObject;
    }

    @Override
    public Boolean updateAccount(Account account) {
        if(account.getId() != null ) {
            log.info("Update Account By Id -> " +account.getId());
            entityManager.merge(account);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteAccount(Account account) {
        log.info("Delete Account By Id -> " +account.getId());
        return accountRepository.costumDeleteById(account.getId()) > 0;
    }
}
