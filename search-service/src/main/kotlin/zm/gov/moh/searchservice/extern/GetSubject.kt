package zm.gov.moh.searchservice.extern

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import zm.gov.moh.searchservice.model.Subject
import java.util.UUID

@HttpExchange
interface GetSubject {
    @GetExchange("/enrolments/{id}")
    fun findById(@PathVariable id: UUID): Subject
}