package zm.gov.moh.enrolmentservice.client

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.FingerprintImageDTO
import zm.gov.moh.enrolmentservice.model.MatchScore

@HttpExchange
interface SearchClient {
    @PostExchange("/search/any")
    fun searchAny(@RequestBody searchPayload: List<FingerprintImageDTO>): Mono<MatchScore>
}