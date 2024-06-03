package zm.gov.moh.consoleservice.client

import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.consoleservice.model.AuthDTO
import zm.gov.moh.consoleservice.model.UserCredentialDTO
import zm.gov.moh.consoleservice.model.UserDTO

@HttpExchange("/auth")
interface IdentityClient {
    @PostExchange("/users")
    fun addUser(@RequestHeader headers: HttpHeaders, @RequestBody userDTO: UserDTO): Mono<UserDTO>

    @GetExchange("/users")
    fun getUsers(@RequestHeader headers: HttpHeaders): Flux<UserDTO>

    @PostExchange("/users/login")
    fun signIn(@RequestBody userCredential: UserCredentialDTO): Mono<AuthDTO>

    @PostExchange("/token/validate")
    fun validateToken(@RequestBody authDTO: AuthDTO): Mono<Boolean>
}