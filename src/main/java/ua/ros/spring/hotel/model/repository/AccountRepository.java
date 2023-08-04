package ua.ros.spring.hotel.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.ros.spring.hotel.model.entity.Account;

import java.util.Optional;

/**
 * Account DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT Account FROM Account WHERE ?1 = ?2")
    Optional<Account> findByField(String field, Object value);

    Optional<Account> findByEmail(String email);

    @Query("SELECT Account FROM Account WHERE ?1 = ?2")
   Optional<Account> update(String field, Object value);

}
