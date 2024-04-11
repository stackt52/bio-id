package zm.gov.moh.identityservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.identityservice.dto.AuthDTO
import zm.gov.moh.identityservice.dto.UserCredentialDTO
import zm.gov.moh.identityservice.dto.UserDTO
import zm.gov.moh.identityservice.entity.User

@Service
class AuthService(
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private val jwtService: JwtService,
    @Autowired
    private val template: R2dbcEntityTemplate
) {

    fun addUser(userDto: UserDTO): Mono<UserDTO> {
        val user = with(userDto) {
            User(name = name, email = email, password = password)
        }
        user.password = passwordEncoder.encode(user.password)

        return template.insert(user)
            .map { i -> UserDTO(i.id, i.name, i.email, i.password) }
    }

    fun findAll(): Flux<UserDTO> {
        return template.select<User>().all().map { i ->
            UserDTO(
                id = i.id,
                name = i.name,
                email = i.email,
                password = ""
            )
        }
    }

    private fun generateToken(userName: String): String {
        return jwtService.generateToken(userName)
    }

    fun validateToken(token: String) {
        jwtService.validateToken(token)
    }

    fun signIn(userCredentials: UserCredentialDTO): Mono<AuthDTO> {
        return template.selectOne(
            Query.query(
                Criteria.where("email").`is`(userCredentials.username)
                    .and(Criteria.where("password").`is`(passwordEncoder.encode(userCredentials.password)))
            ), User::class.java
        ).map { i ->
            AuthDTO(
                token = generateToken(i.email)
            )
        }
    }
}