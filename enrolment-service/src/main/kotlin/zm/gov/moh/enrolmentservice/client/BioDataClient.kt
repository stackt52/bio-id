package zm.gov.moh.enrolmentservice.client

import org.springframework.web.bind.annotation.*
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.FingerprintDTO
import zm.gov.moh.enrolmentservice.model.FingerprintImageDTO

@HttpExchange
interface BioDataClient {
    @PostExchange("/bio-data")
    fun create(@RequestBody fingerPrints: List<FingerprintImageDTO>): Mono<FingerprintDTO>
}