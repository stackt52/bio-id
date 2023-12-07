package zm.gov.moh.searchservice.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import zm.gov.moh.searchservice.model.Subject
import java.util.UUID

@HttpExchange
interface EnrolmentClient {
    @GetExchange("/enrolment/{id}")
    fun findSubjectById(@PathVariable id: UUID): Subject
}