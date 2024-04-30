package zm.gov.moh.identityservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

class CustomReactiveUserDetailsService(
    @Autowired
    private val authService: AuthService
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> {
        return authService.getUser(username)
            .flatMap { i ->
                Mono.just(
                    CustomUserDetails(
                        i.email,
                        "",
                        i.active
                    )
                )
            }
    }
}