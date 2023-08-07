package ua.ros.spring.hotel.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ua.ros.spring.hotel.model.entity.Account;
import ua.ros.spring.hotel.model.entity.QAccount;

import java.util.Optional;


/**
 * Account DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<QAccount> {

    Optional<Account> findByEmail(String email);
    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM account WHERE id = ?1", nativeQuery = true)
    int costumDeleteById(Long id);

}
