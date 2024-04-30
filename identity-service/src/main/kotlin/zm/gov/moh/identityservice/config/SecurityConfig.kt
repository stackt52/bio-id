package zm.gov.moh.identityservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import zm.gov.moh.identityservice.service.AuthService
import zm.gov.moh.identityservice.service.CustomReactiveUserDetailsService


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        jwtTokenAuthFilter: JwtTokenAuthFilter,
        reactiveAuthenticationManager: ReactiveAuthenticationManager
    ): SecurityWebFilterChain {
        return http.cors { i -> i.configurationSource(corsConfigurationSource()) }
            .csrf { i ->
                i.disable()
            }.httpBasic { i -> i.disable() }
            .authenticationManager(reactiveAuthenticationManager)
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) // prevent from creating a WebSession, similar with STATELESS strategy in Servlet stack
            .authorizeExchange { e ->
                e.pathMatchers("/auth/users/login", "/auth/token/validate")
                    .permitAll().anyExchange().authenticated()
            }.addFilterAt(jwtTokenAuthFilter, SecurityWebFiltersOrder.HTTP_BASIC)
            .build()
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf(
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "OPTIONS"
        )
        configuration.allowedHeaders = listOf(
            "Access-Control-Allow-Origin",
            "Authorization",
            "Content-Type"
        )
        //configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun reactiveAuthenticationManager(
        authService: AuthService,
        passwordEncoder: PasswordEncoder
    ): ReactiveAuthenticationManager {
        val authenticationManager =
            UserDetailsRepositoryReactiveAuthenticationManager(CustomReactiveUserDetailsService(authService))
        authenticationManager.setPasswordEncoder(passwordEncoder)
        return authenticationManager
    }

    @Bean
    fun userDetailsService(authService: AuthService): ReactiveUserDetailsService {
        return CustomReactiveUserDetailsService(authService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
