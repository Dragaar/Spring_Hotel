package ua.ros.spring.hotel.model.service.impl.account;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ua.ros.spring.hotel.model.entity.Account;
import ua.ros.spring.hotel.model.repository.AccountRepository;
import ua.ros.spring.hotel.model.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @PersistenceContext
    private EntityManager entityManager;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
    public Account findAccountByField(String field, Object value) {

       /* CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = builder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);

        query.select(root).where(root.get(field).in(value));*/

        //JPAQueryFactory queryFactory=new JPAQueryFactory(entityManager);

       //return accountRepository.findByField(field, value).orElseThrow();
        throw new UnsupportedOperationException();
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
        accountRepository.delete(account);
        return true;
    }
}
