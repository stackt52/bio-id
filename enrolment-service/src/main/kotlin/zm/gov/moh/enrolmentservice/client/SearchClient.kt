package zm.gov.moh.enrolmentservice.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import zm.gov.moh.enrolmentservice.model.FingerprintData
import zm.gov.moh.enrolmentservice.model.Subject

@HttpExchange
interface SearchClient {
    @GetExchange("/search/{id}")
    fun getById(@PathVariable id: String): List<FingerprintData>

    @PostExchange("/search")
    fun search(@RequestBody searchPayload: MutableList<FingerprintData>?): Subject?
}