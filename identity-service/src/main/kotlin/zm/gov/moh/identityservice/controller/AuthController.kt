package zm.gov.moh.identityservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import zm.gov.moh.identityservice.dto.AuthDTO
import zm.gov.moh.identityservice.dto.UserCredentialDTO
import zm.gov.moh.identityservice.dto.UserDTO
import zm.gov.moh.identityservice.service.AuthService
import zm.gov.moh.identityservice.util.UnauthorizedException

@RestController
@RequestMapping("/auth")
class AuthController(
    @Autowired
    private val authService: AuthService
) {
    @PostMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun addUser(@RequestBody userDTO: UserDTO): Mono<UserDTO> {
        return authService.addUser(userDTO)
    }

    @GetMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getUsers(): Flux<UserDTO> {
        return authService.findAll()
    }

    @PostMapping("/users/sign-in", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@RequestBody userCredential: UserCredentialDTO): Mono<AuthDTO> {
        return authService.signIn(userCredential)
            .switchIfEmpty {
                Mono.error(UnauthorizedException("Invalid credentials."))
            }
    }

    @PostMapping("/token/validate", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun validateToken(@RequestBody authDTO: AuthDTO) {
        try {
            return authService.validateToken(authDTO.token)
        } catch (_: Exception) {
            throw UnauthorizedException("Invalid token.")
        }
    }
}