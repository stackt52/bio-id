package zm.gov.moh.searchservice.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.model.FingerprintDTO
import zm.gov.moh.searchservice.model.SubjectDTO
import java.util.*

@HttpExchange
interface EnrolmentClient {
    @GetExchange("/enrolments/{id}")
    fun findById(@PathVariable id: UUID): Mono<SubjectDTO>
}