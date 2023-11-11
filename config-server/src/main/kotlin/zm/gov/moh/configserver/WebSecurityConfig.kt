package zm.gov.moh.configserver


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring() // Spring Security should completely ignore URLs starting with /resources/
                .requestMatchers("/resources/**")
        }
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers("/**")
                it.requestMatchers("/admin/**").hasRole("ADMIN")
                it.anyRequest().hasRole("USER")
            }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user: UserDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build()
        val admin: UserDetails = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("password")
            .roles("ADMIN", "USER")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }
}