package zm.gov.moh.searchservice.client

import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Flux
import zm.gov.moh.searchservice.model.FingerprintDao

@HttpExchange
interface BioDataClient {
    @GetExchange("/bio-data")
    fun getAllBioData(): Flux<FingerprintDao>
}