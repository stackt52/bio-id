package zm.gov.moh.consoleservice.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.DeleteExchange
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import org.springframework.web.service.annotation.PutExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.consoleservice.model.ClientDTO
import zm.gov.moh.consoleservice.model.EnrolmentJsonDTO
import java.util.*

@HttpExchange("/enrolments")
interface EnrolmentClient {
    @PostExchange("/json")
    fun enroll(@RequestBody enrolmentJsonDTO: EnrolmentJsonDTO): Mono<ClientDTO>

    @GetExchange
    fun findAll(): Flux<ClientDTO>

    @GetExchange("/{id}")
    fun findById(@PathVariable id: UUID): Mono<ClientDTO>

    @PutExchange("/{id}")
    fun updateById(@PathVariable id: UUID, @RequestBody subject: ClientDTO): Mono<ClientDTO>

    @DeleteExchange("/{id}")
    fun deleteById(@PathVariable id: String): Mono<ClientDTO>
}