package zm.gov.moh.enrolmentservice.client

import org.springframework.web.bind.annotation.*
import org.springframework.web.service.annotation.DeleteExchange
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import org.springframework.web.service.annotation.PutExchange
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.FingerprintDTO
import java.util.*

@HttpExchange
interface BioDataClient {

    @PostExchange("/bio-data")
    fun create(@RequestBody fingerPrint: FingerprintDTO): Mono<FingerprintDTO>

    @GetExchange("/bio-data/{subjectId}")
    fun findById(@PathVariable subjectId: UUID): Mono<FingerprintDTO>

    @PutExchange("/bio-data/{subjectId}")
    fun update(@RequestBody fingerPrint: FingerprintDTO): Mono<FingerprintDTO>

    @DeleteExchange("/bio-data/{subjectId}")
    fun delete(@PathVariable subjectId: UUID): Mono<FingerprintDTO>
}