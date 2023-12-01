package zm.gov.moh.searchservice.extern

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Flux
import zm.gov.moh.searchservice.model.FingerprintDao
import zm.gov.moh.searchservice.model.FingerprintData
import java.util.UUID

@HttpExchange
interface GetFingerprintData {
    @GetExchange("/bio-data/src-system/{srcSystemId}")
    fun getBySrcSystemId(@PathVariable srcSystemId: String): Flux<FingerprintDao>
}