package zm.gov.moh.apigateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { csrf -> csrf.disable() }
                .authorizeHttpRequests {
                    auth -> auth.requestMatchers("/eureka/**").permitAll()
                    auth.anyRequest().authenticated()
                }
                .oauth2ResourceServer { oauth -> oauth.jwt(Customizer.withDefaults()) }

        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/realms/npbvs/protocol/openid-connect/token").build()
    }
}