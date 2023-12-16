package zm.gov.moh.searchservice.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Flux
import zm.gov.moh.searchservice.model.FingerprintDao
import java.util.UUID

@HttpExchange
interface BioDataClient {
    @GetExchange("/bio-data/src-system/{srcSystemId}")
    fun findFingerprintDataBySrcSystemId(@PathVariable srcSystemId: UUID): Flux<FingerprintDao>
}