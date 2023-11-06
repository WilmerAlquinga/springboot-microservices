package dev.wsalquinga.accounts_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wsalquinga on 27/10/2023
 */
@Configuration
public class OpenAPIConfig {
    @Value("${wsalquinga.openapi.local-url}")
    private String localUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl(localUrl);
        localServer.setDescription("Server URL in Local environment");

        Contact contact = new Contact();
        contact.setEmail("wilmer.alquinga21@gmail.com");
        contact.setName("Wilmer Alquinga");
        contact.setUrl("");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Accounts and Transactions Service API - Technical assessment")
                .version("1.0.0")
                .contact(contact)
                .description("This API exposes endpoints to manage accounts and movements.").termsOfService("")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(localServer));
    }
}
