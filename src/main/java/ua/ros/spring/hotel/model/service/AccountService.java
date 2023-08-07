package ua.ros.spring.hotel.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.ros.spring.hotel.model.entity.Account;

import java.sql.Connection;
import java.util.Optional;
import com.querydsl.core.types.Predicate;

/**
 * Account Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface AccountService {

    /**
     * Create new account
     * @param account the account to be added
     * @return operation result
     */
    Boolean         createAccount(Account account);

    /**
     * Check if account exist
     * @param account the account to be checked
     * @return operation result
     */
    Boolean         isAccountExist(Account account);

    /**
     * Find account by predicate
     * @param p conditions to receive account
     * @return found account
     */
    Optional<Account>   findOne(Predicate p);

    /**
     * Find accounts by predicate
     * @param p conditions to receive accounts
     * @param pageable conditions to perform paginating
     * @return page with accounts
     */
    Page<Account> findAll(Predicate p, Pageable pageable);

    /**
     * Update account
     * @param account account to update
     * @return operation result
     */
    Boolean         updateAccount(Account account);
    /**
     * Delete account
     * @param account account to delete
     * @return operation result
     */
    Boolean         deleteAccount(Account account);
}
