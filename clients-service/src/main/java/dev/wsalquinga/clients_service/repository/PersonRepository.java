package dev.wsalquinga.clients_service.repository;

import dev.wsalquinga.clients_service.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author wsalquinga on 26/10/2023
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("select p from Person p " +
            "where (p.deletedAt is null " +
            "or p.deletedAt > current_timestamp) " +
            "and p.id = :id")
    Optional<Person> findValidById(Long id);
}
