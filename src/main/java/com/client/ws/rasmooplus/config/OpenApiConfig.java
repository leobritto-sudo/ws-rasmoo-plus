package com.client.ws.rasmooplus.config;

import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("${openapi.url}")
    private String url;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription("Url localhost");

        License license = new License().name("Rasmoo Cursos e tecnologia");

        Info info = new Info()
                .title("Rasmoo Plus")
                .version("0.0.1")
                .description("API Rest para atender as demandas do client Rasmoo Plus")
                .license(license);

        return new OpenAPI().info(info).servers(List.of(server));
    }
}
