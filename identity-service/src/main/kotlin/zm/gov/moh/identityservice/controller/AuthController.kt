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
@CrossOrigin
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

    @PutMapping("/users/{username}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUser(@PathVariable username: String, @RequestBody userCredentialDTO: UserCredentialDTO): Mono<UserDTO> {
        return authService.updatePassword(username, userCredentialDTO)
            .flatMap { Mono.empty() }
    }

    @PostMapping("/users/login", produces = [MediaType.APPLICATION_JSON_VALUE])
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
        if (!authService.validateToken(authDTO.token)) {
            throw UnauthorizedException("Invalid token.")
        }
    }
}