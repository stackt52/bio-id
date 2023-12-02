package zm.gov.moh.searchservice.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import zm.gov.moh.searchservice.extern.GetSubject
import zm.gov.moh.searchservice.extern.GetFingerprintData
import zm.gov.moh.searchservice.model.FingerprintDao
import zm.gov.moh.searchservice.model.Subject
import java.util.UUID

@Repository
class SearchRepository(
    @Autowired
    private val getFingerprintData: GetFingerprintData,
    @Autowired
    private val getSubject: GetSubject
) {
    fun findFingerprintDaoBySrcSystemId(srcSystemId: UUID): Flux<FingerprintDao> {
        return getFingerprintData.getBySrcSystemId(srcSystemId)
    }

    fun findSubjectBySubjectId(subjectId: UUID): Subject {
        return getSubject.findById(subjectId)
    }
}