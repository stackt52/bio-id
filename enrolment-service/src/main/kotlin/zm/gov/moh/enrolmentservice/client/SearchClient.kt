package zm.gov.moh.enrolmentservice.client

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.dto.SearchDTO
import zm.gov.moh.enrolmentservice.model.FingerprintData
import zm.gov.moh.enrolmentservice.model.SearchPayload
import zm.gov.moh.enrolmentservice.model.Subject

@HttpExchange
interface SearchClient {

    @PostExchange("/search")
    fun search(@RequestBody search: SearchPayload): Mono<SearchDTO>
}