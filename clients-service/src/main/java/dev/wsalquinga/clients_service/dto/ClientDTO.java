package dev.wsalquinga.clients_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wsalquinga on 26/10/2023
 */
@Getter
@Setter
@Builder
public class ClientDTO {

    private Long id;

    private String password;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean status;

    @NotBlank(message = "Nombre es requerido")
    private String name;

    @NotBlank(message = "Género es requerido")
    private String gender;

    private Integer age;

    @NotBlank(message = "Número de identificación es requerido")
    private String documentNumber;

    private String address;

    private String phoneNumber;
}
