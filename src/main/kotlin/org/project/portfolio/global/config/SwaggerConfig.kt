package org.project.portfolio.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(configurationInfo())
    }

    private fun configurationInfo(): Info {
        return Info()
            .title("OpenAPI3 UI 테스트")
            .description("OpenAPI3 - Springdoc을 사용한 Swagger UI 테스트")
            .version("1.0.0")
    }
}