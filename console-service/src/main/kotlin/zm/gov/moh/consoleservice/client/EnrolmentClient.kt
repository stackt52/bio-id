package zm.gov.moh.consoleservice.client

import org.springframework.web.bind.annotation.ModelAttribute
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
import zm.gov.moh.consoleservice.model.EnrolmentDTO
import java.util.*

@HttpExchange
interface EnrolmentClient {
    @PostExchange("/enrolments")
    suspend fun add(@ModelAttribute subjectDetails: EnrolmentDTO): ClientDTO

    @GetExchange("/enrolments")
    fun findAll(): Flux<ClientDTO>

    @GetExchange("/enrolments/{id}")
    fun findById(@PathVariable id: UUID): Mono<ClientDTO>

    @PutExchange("/enrolments/{id}")
    fun updateById(@PathVariable id: UUID, @RequestBody subject: ClientDTO): Mono<ClientDTO>

    @DeleteExchange("/enrolments/{id}")
    fun deleteById(@PathVariable id: String): Mono<ClientDTO>
}