package com.musalasoft.drone.drone.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {


  @Bean
  OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("Drone Management API")
            .description("API for managing drones and their payloads").version("1.0")
            .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")))
        .externalDocs(new ExternalDocumentation().description("Project Documentation")
            .url("https://github.com/MahmoudMostafa76/Drone-MusalaSoft"));
  }
}
