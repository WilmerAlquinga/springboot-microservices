package dev.wsalquinga.clients_service.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wsalquinga on 29/10/2023
 */
@Getter
@AllArgsConstructor
public class ClientProjectionDTO {
    private Long id;

    @Value("#{target.person.name}")
    private String name;
}
