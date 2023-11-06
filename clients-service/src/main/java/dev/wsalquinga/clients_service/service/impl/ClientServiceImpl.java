package dev.wsalquinga.clients_service.service.impl;

import dev.wsalquinga.clients_service.dto.ClientDTO;
import dev.wsalquinga.clients_service.dto.projection.ClientProjectionDTO;
import dev.wsalquinga.clients_service.entity.Client;
import dev.wsalquinga.clients_service.entity.Person;
import dev.wsalquinga.clients_service.exception.ResourceNotFoundException;
import dev.wsalquinga.clients_service.mapper.ClientMapper;
import dev.wsalquinga.clients_service.repository.ClientRepository;
import dev.wsalquinga.clients_service.repository.PersonRepository;
import dev.wsalquinga.clients_service.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wsalquinga on 26/10/2023
 */
@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PersonRepository personRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, PersonRepository personRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.personRepository = personRepository;
    }

    @Override
    public Client getClientById(Long id) {
        return this.clientRepository.findValidById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el Cliente con id: " + id));
    }

    @Override
    public ClientDTO getById(Long id) {
        return this.clientMapper.toClientDTO(this.getClientById(id));
    }

    @Override
    public List<ClientDTO> getAll() {
        return this.clientMapper.toClientDTO(this.clientRepository.findAllValid());
    }

    @Override
    @Transactional
    public ClientDTO create(ClientDTO clientDTO) {
        Person person = this.personRepository.save(
                Person.builder()
                        .name(clientDTO.getName())
                        .gender(clientDTO.getGender())
                        .age(clientDTO.getAge())
                        .documentNumber(clientDTO.getDocumentNumber())
                        .address(clientDTO.getAddress())
                        .phoneNumber(clientDTO.getPhoneNumber())
                        .build());
        log.info("Person created: {}", person);

        Client client = this.clientMapper.toEntity(clientDTO);
        client.setPerson(person);
        client = this.clientRepository.save(client);
        log.info("Client created: {}", client);
        return this.clientMapper.toClientDTO(client);
    }

    @Override
    @Transactional
    public ClientDTO update(ClientDTO clientDTO, Long id) {
        Client client = this.getClientById(id);
        Person person = client.getPerson();
        person.setName(clientDTO.getName());
        person.setGender(clientDTO.getGender());
        person.setAge(clientDTO.getAge());
        person.setDocumentNumber(clientDTO.getDocumentNumber());
        person.setAddress(clientDTO.getAddress());
        Person updatedPerson = this.personRepository.save(person);
        log.info("Person updated: {}", updatedPerson);

        client.setPassword(clientDTO.getPassword());
        client.setStatus(clientDTO.getStatus());
        client.setPerson(updatedPerson);
        client = this.clientRepository.save(client);
        log.info("Client updated: {}", client);
        return this.clientMapper.toClientDTO(client);
    }

    @Override
    public void delete(Long id) {
        Client client = this.getClientById(id);
        client.setDeletedAt(LocalDateTime.now());
        this.clientRepository.save(client);
        log.info("Client with id: {} successfully deleted", id);
    }

    @Override
    public ClientProjectionDTO getNameById(Long id) {
        return this.clientRepository.findNameById(id).orElseThrow(
                () -> new ResourceNotFoundException("No se encontró el Cliente con id: " + id));
    }

    @Override
    public List<ClientProjectionDTO> getNamesByIds(List<Long> ids) {
        return this.clientRepository.findNamesByIds(ids);
    }
}
