/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (c) 2026 Jumbo Supermarkten B.V.
 * All rights reserved.
 *
 * This source code is part of the Jumbo Store Locator demo application.
 * Unauthorized copying or distribution is prohibited.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 */

package com.jumbo.store.locator.appconfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(
            @Value("${openapi.server-url:http://localhost:${server.port}}") String serverUrl,
            @Value("${openapi.version:v1}") String version
    ) {

        Server server = new Server()
                .url(serverUrl)
                .description("Configured API server");

        Info apiInfo = new Info()
                .title("Jumbo Store Locator API")
                .version(version)
                .description("""
                        REST API for retrieving Jumbo store information based on geolocation.

                        Error Codes:
                        - NO_STORES_FOUND: No stores are available in the store dataset.
                        - VALIDATION_ERROR: Request parameters failed validation.
                        - INTERNAL_ERROR: Unexpected server-side failure.
                        """);


        return new OpenAPI()
                .info(apiInfo)
                .addServersItem(server);
    }
}
