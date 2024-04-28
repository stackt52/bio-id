package zm.gov.moh.identityservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
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
import zm.gov.moh.identityservice.util.Claim
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Service
class AuthService(
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private val jwtService: JwtService,
    @Autowired
    private val template: R2dbcEntityTemplate
) : ApplicationListener<ApplicationStartedEvent> {

    fun addUser(userDto: UserDTO): Mono<UserDTO> {
        val user = with(userDto) {
            User(
                name = name,
                email = email,
                password = passwordEncoder.encode(password),
                active = active,
                lastDateUpdated = lastDateUpdated,
                lastTimeUpdated = lastTimeUpdate
            )
        }

        return template.insert(user)
            .map { i -> UserDTO(i.id, i.name, i.email, "", i.active, i.lastDateUpdated, i.lastTimeUpdated) }
    }

    fun findAll(): Flux<UserDTO> {
        return template.select<User>().all().map { i ->
            UserDTO(
                id = i.id,
                name = i.name,
                email = i.email,
                password = "",
                i.active,
                i.lastDateUpdated,
                i.lastTimeUpdated
            )
        }
    }

    fun updatePassword(username: String, userCredentials: UserCredentialDTO): Mono<UserDTO> {
        return template.selectOne(
            Query.query(Criteria.where("email").`is`(username)),
            User::class.java
        ).flatMap { i ->
            i.email = userCredentials.username
            i.password = passwordEncoder.encode(userCredentials.password)
            i.lastDateUpdated = LocalDate.now()
            i.lastTimeUpdated = LocalTime.now()
            template.update(i)
        }.map { i ->
            UserDTO(
                id = i.id,
                name = i.name,
                email = i.email,
                password = "",
                i.active,
                i.lastDateUpdated,
                i.lastTimeUpdated
            )
        }
    }

    private fun generateToken(userName: String, claims: Map<String, Any>): String {
        return jwtService.generateToken(userName, claims)
    }

    fun validateToken(token: String): Boolean {
        return try {
            jwtService.validateToken(token)
            true
        } catch (_: Exception) {
            false
        }
    }

    fun getUser(email: String): Mono<UserDTO> {
        return template.selectOne(
            Query.query(
                Criteria.where("email").`is`(email)
            ), User::class.java
        ).map { i ->
            with(i) {
                UserDTO(
                    id = id,
                    email = email,
                    name = name,
                    password = password,
                    active = active,
                    lastDateUpdated = lastDateUpdated,
                    lastTimeUpdate = lastTimeUpdated
                )
            }
        }
    }

    fun signIn(userCredentials: UserCredentialDTO): Mono<AuthDTO> {
        return getUser(userCredentials.username).flatMap { i ->
            if (passwordEncoder.matches(userCredentials.password, i.password))
                Mono.just(i)
            else
                Mono.empty()
        }.map { i ->
            val claims = mapOf(
                Pair<String, Any>(Claim.SOURCE_SYS_ID.name, i.id!!),
                Pair<String, Any>(Claim.USERNAME.name, i.email)
            )
            val(_, name, username) = i
            AuthDTO(
                username,
                name,
                token = generateToken(username, claims)
            )
        }
    }

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        val userDto = UserDTO(
            id = UUID.fromString("00000000-0000-0000-0000-000000000000"),
            name = "admin",
            email = "admin@bioid.com",
            password = "default"
        )
        getUser(userDto.email).switchIfEmpty(addUser(userDto)).subscribe()
    }
}