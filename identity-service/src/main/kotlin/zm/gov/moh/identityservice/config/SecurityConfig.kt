package zm.gov.moh.identityservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import zm.gov.moh.identityservice.service.UserDetailsService


@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    @Autowired
    private val userDetailsService: UserDetailsService
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http.csrf { i ->
            i.disable()
        }.authorizeExchange { e ->
            e.pathMatchers("/auth/users/sign-in", "/auth/token/validate")
                .permitAll()
        }.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun reactiveAuthenticationManager(): ReactiveAuthenticationManager {
        return ReactiveAuthenticationManager { authentication: Authentication ->
            val authenticator = UserDetailsRepositoryReactiveAuthenticationManager(
                userDetailsService
            )
            authenticator.setPasswordEncoder(passwordEncoder())
            authenticator.authenticate(authentication)
        }
    }
}
