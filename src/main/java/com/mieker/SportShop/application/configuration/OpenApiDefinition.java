package com.mieker.SportShop.application.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiDefinition {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .description("Sport Shop API for orders managing with Mongo DB.")
                        .title("Sport Shop Orders API "))
                .components(new Components()
                        .addSecuritySchemes("Basic Authorization", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                                .description("Basic Authorization Scheme")))
                .security(List.of(new SecurityRequirement().addList("Basic Authorization")));
    }

}
