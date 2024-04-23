package zm.gov.moh.identityservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import zm.gov.moh.identityservice.entity.User
import zm.gov.moh.identityservice.util.CustomUserDetails

@Service
class UserDetailsService(
    @Autowired
    private val template: R2dbcEntityTemplate
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> {
        return template.selectOne(
            Query.query(
                Criteria.where("email").`is`(username)
            ), User::class.java
        ).map { i ->
            with(i) {
                CustomUserDetails(email, password, active)
            }
        }
    }
}