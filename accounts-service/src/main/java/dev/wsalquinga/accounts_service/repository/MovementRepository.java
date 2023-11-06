package dev.wsalquinga.accounts_service.repository;

import dev.wsalquinga.accounts_service.entity.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author wsalquinga on 28/10/2023
 */
@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    @Query("select m from Movement m " +
            "where (m.deletedAt is null " +
            "or m.deletedAt > current_timestamp) " +
            "and m.id = :id")
    Optional<Movement> findValidById(Long id);

    @Query("select m from Movement m " +
            "where (m.deletedAt is null " +
            "or m.deletedAt > current_timestamp)")
    List<Movement> findAllValid();

    @Query("select m from Movement m " +
            "where (m.deletedAt is null " +
            "or m.deletedAt > current_timestamp) " +
            "and m.account.clientId = :clientId " +
            "and m.date between :from and :to " +
            "order by m.account.accountNumber")
    Page<Movement> findByClientIdAndBetweenDates(Long clientId, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
