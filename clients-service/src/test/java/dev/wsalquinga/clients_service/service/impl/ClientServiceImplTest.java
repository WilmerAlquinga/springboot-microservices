package dev.wsalquinga.clients_service.service.impl;

import dev.wsalquinga.clients_service.entity.Client;
import dev.wsalquinga.clients_service.entity.Person;
import dev.wsalquinga.clients_service.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;


/**
 * @author wsalquinga on 7/11/2023
 */
@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    private void setup() {
        Person person = Person.builder()
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
                .id(1L)
                .password("password")
                .status(Boolean.TRUE)
                .person(person)
                .createdAt(LocalDateTime.now())
                .createdBy("Guest User")
                .updatedAt(LocalDateTime.now())
                .updatedBy("Guest User")
                .deletedAt(null)
                .build();
    }

    @Test
    @DisplayName("Unit Test for get Client by id method")
    void getClientById() {
        given(clientRepository.findValidById(1L)).willReturn(Optional.of(client));

        Client getClient = clientService.getClientById(client.getId());

        assertNotNull(getClient);
    }
}