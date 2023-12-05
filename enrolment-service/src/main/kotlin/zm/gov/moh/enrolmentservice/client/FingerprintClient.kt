package zm.gov.moh.enrolmentservice.client

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.service.annotation.DeleteExchange
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import org.springframework.web.service.annotation.PutExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.FingerprintDao
import zm.gov.moh.enrolmentservice.model.FingerprintData
import java.util.*

@HttpExchange
interface FingerprintClient {

    @PostExchange("/bio-data")
    fun create(@RequestBody fingerPrint: FingerprintDao): Mono<Boolean>

    @GetExchange("/bio-data/{subjectId}")
    fun findById(@PathVariable subjectId: UUID): Mono<FingerprintDao>?

    @GetExchange("/bio-data")
    fun findAll(): Flux<FingerprintDao>

    @GetExchange("/bio-data/src-system/{sourceSystemId}")
    fun findBySrcSystemId(@PathVariable sourceSystemId: UUID): Flux<FingerprintData>?

    @PutExchange("/bio-data/{subjectId}")
    fun update(@RequestBody fingerPrint: FingerprintDao): Mono<Boolean>

    @DeleteExchange("/bio-data/{subjectId}")
    fun delete(@PathVariable subjectId: UUID): Mono<Boolean>
}