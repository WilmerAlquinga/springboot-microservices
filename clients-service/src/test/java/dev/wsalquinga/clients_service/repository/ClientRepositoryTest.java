package dev.wsalquinga.clients_service.repository;

import dev.wsalquinga.clients_service.entity.Client;
import dev.wsalquinga.clients_service.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wsalquinga on 7/11/2023
 */
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PersonRepository personRepository;

    private Client client;
    
    private Person person;

    @BeforeEach
    public void setup() {
        person = Person.builder()
                .name("User 1")
                .gender("Masculino")
                .age(25)
                .documentNumber("1234567890")
                .address("Quito")
                .phoneNumber("0987654321")
                .createdAt(LocalDateTime.now())
                .createdBy("Guest User")
                .updatedAt(LocalDateTime.now())
                .updatedBy("Guest User")
                .deletedAt(null)
                .build();
        client = Client.builder()
                .password("password")
                .status(Boolean.TRUE)
                .createdAt(LocalDateTime.now())
                .createdBy("Guest User")
                .updatedAt(LocalDateTime.now())
                .updatedBy("Guest User")
                .deletedAt(null)
                .build();
    }

    @Test
    @DisplayName("Unit Test for find an existent Client")
    void testFindValidByIdExistent() {
        Person savedPerson = personRepository.save(person);
        client.setPerson(savedPerson);
        Client savedClient = clientRepository.save(client);

        Client getClient = clientRepository.findValidById(savedClient.getId()).orElse(null);

        assertNotNull(getClient);
        assertTrue(getClient.getId() > 0L);
    }

    @Test
    @DisplayName("Unit Test for find an non existent Client")
    void testFindValidByIdNonExistent() {
        Client getClient = clientRepository.findValidById(3L).orElse(null);

        assertNull(getClient);
    }

    @Test
    @DisplayName("Unit Test for find all Clients")
    void findAllValid() {
        Person person2 = Person.builder()
                .name("User 2")
                .gender("Femenino")
                .age(25)
                .documentNumber("1234567899")
                .address("Guayaquil")
                .phoneNumber("0987654322")
                .createdAt(LocalDateTime.now())
                .createdBy("Guest User")
                .updatedAt(LocalDateTime.now())
                .updatedBy("Guest User")
                .deletedAt(null)
                .build();
        Client client2 = Client.builder()
                .password("password")
                .status(Boolean.TRUE)
                .createdAt(LocalDateTime.now())
                .createdBy("Guest User")
                .updatedAt(LocalDateTime.now())
                .updatedBy("Guest User")
                .deletedAt(null)
                .build();
        Person savedPerson = personRepository.save(person);
        Person savedPerson2 = personRepository.save(person2);
        client.setPerson(savedPerson);
        clientRepository.save(client);
        client2.setPerson(savedPerson2);
        clientRepository.save(client2);

        List<Client> clients = clientRepository.findAllValid();

        assertFalse(clients.isEmpty());
        assertEquals(2, clients.size());
    }
}