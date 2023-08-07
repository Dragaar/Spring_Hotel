package ua.ros.spring.hotel.model.service.impl.account;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Account;
import ua.ros.spring.hotel.model.entity.QAccount;
import ua.ros.spring.hotel.model.repository.AccountRepository;
import ua.ros.spring.hotel.model.service.AccountService;


import java.util.Optional;
import com.querydsl.core.types.Predicate;

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
            entityManager.persist(account);
            return true;
        }
        return false;
    }

    @Transactional(readOnly=true)
    @Override
    public Boolean isAccountExist(Account account) {
        Account tempAccount = accountRepository.findById(account.getId()).orElseThrow();
        return tempAccount.equals(account);
    }

    @Transactional(readOnly=true)
    @Override
    public Optional<Account> findOne(Predicate predicate) {
        Optional<QAccount> qAccount = accountRepository.findOne(predicate);
        return qAccount.map(qAcc -> modelMapper.map(qAcc, Account.class));
    }

    @Transactional(readOnly=true)
    @Override
    public Page<Account> findAll(Predicate predicate, Pageable pageable) {
        Page<QAccount> qAccounts = accountRepository.findAll(predicate, pageable);
        return qAccounts.map(qAcc -> modelMapper.map(qAcc, Account.class));
    }

    @Override
    public Boolean updateAccount(Account account) {
        if(account.getId() != null ) {
            entityManager.merge(account);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteAccount(Account account) {
        return accountRepository.costumDeleteById(account.getId()) > 0;
    }
}
