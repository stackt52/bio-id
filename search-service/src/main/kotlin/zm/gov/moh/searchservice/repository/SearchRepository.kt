package zm.gov.moh.searchservice.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import zm.gov.moh.searchservice.client.EnrolmentClient
import zm.gov.moh.searchservice.client.BioDataClient
import zm.gov.moh.searchservice.model.FingerprintDao
import zm.gov.moh.searchservice.model.Subject
import java.util.UUID

@Repository
class SearchRepository(
    @Autowired
    private val bioDataClient: BioDataClient,
    @Autowired
    private val enrolmentClient: EnrolmentClient
) {
    fun findFingerprintBySrcSystemId(srcSystemId: UUID): Flux<FingerprintDao> {
        return bioDataClient.findFingerprintDataBySrcSystemId(srcSystemId)
    }

    fun findSubjectBySubjectId(subjectId: UUID): Subject {
        return enrolmentClient.findSubjectById(subjectId)
    }
}