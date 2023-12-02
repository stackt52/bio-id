package zm.gov.moh.enrolmentservice.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.FingerprintDao
import java.util.*

@HttpExchange
interface FingerprintClient {
    @GetExchange("/bio-data/{subjectId}")
    fun findById(@PathVariable subjectId: UUID): Mono<FingerprintDao>?

    @GetExchange("/bio-data")
    fun findAll(): Flux<FingerprintDao>

    @PostExchange("/bio-data")
    fun create(@RequestBody fingerPrint: FingerprintDao): Mono<Boolean>
}