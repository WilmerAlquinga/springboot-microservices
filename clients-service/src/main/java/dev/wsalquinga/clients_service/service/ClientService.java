package dev.wsalquinga.clients_service.service;

import dev.wsalquinga.clients_service.dto.ClientDTO;
import dev.wsalquinga.clients_service.dto.projection.ClientProjectionDTO;
import dev.wsalquinga.clients_service.entity.Client;

import java.util.List;

/**
 * @author wsalquinga on 26/10/2023
 */
public interface ClientService {
    Client getClientById(Long id);

    ClientDTO getById(Long id);

    List<ClientDTO> getAll();

    ClientDTO create(ClientDTO clientDTO);

    ClientDTO update(ClientDTO clientDTO, Long id);

    void delete(Long id);

    ClientProjectionDTO getNameById(Long id);

    List<ClientProjectionDTO> getNamesByIds(List<Long> ids);
}
