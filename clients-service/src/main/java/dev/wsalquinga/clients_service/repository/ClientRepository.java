package dev.wsalquinga.clients_service.repository;

import dev.wsalquinga.clients_service.dto.projection.ClientProjectionDTO;
import dev.wsalquinga.clients_service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author wsalquinga on 26/10/2023
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client c " +
            "where (c.deletedAt is null " +
            "or c.deletedAt > current_timestamp) " +
            "and c.id = :id")
    Optional<Client> findValidById(Long id);

    @Query("select c from Client c " +
            "where (c.deletedAt is null " +
            "or c.deletedAt > current_timestamp)")
    List<Client> findAllValid();

    @Query("select new dev.wsalquinga.clients_service.dto.projection.ClientProjectionDTO(c.id, c.person.name) from Client c " +
            "where (c.deletedAt is null " +
            "or c.deletedAt > current_timestamp) " +
            "and (c.person.deletedAt is null " +
            "or c.person.deletedAt > current_timestamp) " +
            "and c.id = :id")
    Optional<ClientProjectionDTO> findNameById(Long id);

    @Query("select new dev.wsalquinga.clients_service.dto.projection.ClientProjectionDTO(c.id, c.person.name) from Client c " +
            "where (c.deletedAt is null " +
            "or c.deletedAt > current_timestamp) " +
            "and (c.person.deletedAt is null " +
            "or c.person.deletedAt > current_timestamp) " +
            "and c.id in (:ids)")
    List<ClientProjectionDTO> findNamesByIds(List<Long> ids);
}
