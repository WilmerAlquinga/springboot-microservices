package dev.wsalquinga.clients_service.controller;

import dev.wsalquinga.clients_service.common.GlobalConstant;
import dev.wsalquinga.clients_service.common.enums.ExceptionMessageEnum;
import dev.wsalquinga.clients_service.dto.ClientDTO;
import dev.wsalquinga.clients_service.exception.ResourceNotFoundException;
import dev.wsalquinga.clients_service.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author wsalquinga on 6/11/2023
 */
@WebMvcTest(ClientController.class)
class ClientControllerTest {
    private static final String CLIENTS_PATH = GlobalConstant.API_V1_VERSION + "/clients";

    private ClientDTO clientOne, clientTwo;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ClientService clientService;

    @BeforeEach
    public void setup() {
        clientOne = ClientDTO.builder()
                .id(1L)
                .password("password")
                .status(Boolean.TRUE)
                .name("User 1")
                .gender("Masculino")
                .age(25)
                .documentNumber("1234567890")
                .address("Quito")
                .phoneNumber("0987654321").build();
        clientTwo = ClientDTO.builder()
                .id(2L)
                .password("password")
                .status(Boolean.TRUE)
                .name("User 2")
                .gender("Femenino")
                .age(26)
                .documentNumber("1234567899")
                .address("Guayaquil")
                .phoneNumber("0987654322").build();
    }

    @Test
    @DisplayName("Unit Test for get all Clients")
    void testListAllReturn200Ok() throws Exception {
        List<ClientDTO> clients = new ArrayList<>();
        clients.add(clientOne);
        clients.add(clientTwo);
        given(this.clientService.getAll()).willReturn(clients);

        ResultActions response = mockMvc.perform(get(CLIENTS_PATH));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(clients.size())))
                .andDo(print());
    }

    @Test
    @DisplayName("Unit Test for get Client by Id - Success")
    void testFindByIdReturn200Ok() throws Exception {
        long clientId = 1L;
        given(clientService.getById(clientId)).willReturn(clientOne);

        ResultActions response = mockMvc.perform(get(CLIENTS_PATH + "/{id}", clientId));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is(clientOne.getName())))
                .andExpect(jsonPath("$.documentNumber", is(clientOne.getDocumentNumber())))
                .andExpect(jsonPath("$.status", is(clientOne.getStatus())))
                .andDo(print());
    }

    @Test
    @DisplayName("Unit Test for get Client by Id - Not Found")
    void testFindByIdReturn404NotFound() throws Exception {
        long clientId = 1L;
        given(clientService.getById(clientId)).willThrow(
                new ResourceNotFoundException(ExceptionMessageEnum.CLIENT_NOT_FOUND.getMessage() + clientId));

        ResultActions response = mockMvc.perform(get(CLIENTS_PATH + "/{id}", clientId));

        response.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", is(ExceptionMessageEnum.CLIENT_NOT_FOUND.getMessage() + clientId)))
                .andDo(print());
    }

    // TODO implements unit tests of the remaining methods
}