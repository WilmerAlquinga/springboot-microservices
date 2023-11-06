package dev.wsalquinga.clients_service.mapper;

import dev.wsalquinga.clients_service.dto.ClientDTO;
import dev.wsalquinga.clients_service.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author wsalquinga on 26/10/2023
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.gender", target = "gender")
    @Mapping(source = "person.age", target = "age")
    @Mapping(source = "person.documentNumber", target = "documentNumber")
    @Mapping(source = "person.address", target = "address")
    @Mapping(source = "person.phoneNumber", target = "phoneNumber")
    ClientDTO toClientDTO(Client client);

    List<ClientDTO> toClientDTO(List<Client> client);

    @Mapping(target = "person", ignore = true)
    Client toEntity(ClientDTO clientDTO);
}
