package zm.gov.moh.consoleservice.controller

import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.consoleservice.client.IdentityClient
import zm.gov.moh.consoleservice.model.AuthDTO
import zm.gov.moh.consoleservice.model.UserCredentialDTO
import zm.gov.moh.consoleservice.model.UserDTO
import zm.gov.moh.consoleservice.util.UnauthorizedException

@RestController
@RequestMapping("/console/auth")
@Api(tags = ["", "Authentication"], description = "Console endpoint")
class ConsoleIdentityController(
    @Autowired
    private val identityClient: IdentityClient,
) {
    @PostMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun addUser(@RequestHeader headers: HttpHeaders, @RequestBody userDTO: UserDTO): Mono<UserDTO> {
        return identityClient.addUser(headers, userDTO)
    }

    @GetMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getUsers(@RequestHeader header: HttpHeaders): Flux<UserDTO> {
        return identityClient.getUsers(header)
    }

    @PostMapping("/users/login", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@RequestBody userCredential: UserCredentialDTO): Mono<AuthDTO> {
        return identityClient.signIn(userCredential)
            .onErrorMap { i -> UnauthorizedException("Invalid username or password") }

    }

    @PostMapping("/token/validate", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun validateToken(@RequestBody authDTO: AuthDTO): Mono<Boolean> {
        return identityClient.validateToken(authDTO)
    }
}