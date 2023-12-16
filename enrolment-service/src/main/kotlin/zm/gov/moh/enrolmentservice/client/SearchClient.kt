package zm.gov.moh.enrolmentservice.client

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.FingerprintData
import zm.gov.moh.enrolmentservice.model.Subject

@HttpExchange
interface SearchClient {

    @PostExchange("/search")
    fun search(@RequestBody searchPayload: MutableList<FingerprintData>): Mono<Subject>
}