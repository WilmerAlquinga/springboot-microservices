package dev.wsalquinga.accounts_service.repository;

import dev.wsalquinga.accounts_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author wsalquinga on 28/10/2023
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a from Account a " +
            "where (a.deletedAt is null " +
            "or a.deletedAt > current_timestamp) " +
            "and a.id = :id")
    Optional<Account> findValidById(Long id);

    @Query("select a from Account a " +
            "where (a.deletedAt is null " +
            "or a.deletedAt > current_timestamp)")
    List<Account> findAllValid();

    @Query("select case when COUNT(a) > 0 then TRUE else FALSE end from Account a " +
            "where (a.deletedAt is null " +
            "or a.deletedAt > current_timestamp) " +
            "and a.accountNumber = :accountNumber")
    boolean existsByAccountNumber(String accountNumber);
}
