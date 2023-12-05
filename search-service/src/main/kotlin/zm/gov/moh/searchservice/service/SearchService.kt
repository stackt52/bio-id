package zm.gov.moh.searchservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.model.FingerprintDao
import zm.gov.moh.searchservice.model.SearchPayload
import zm.gov.moh.searchservice.model.Subject
import zm.gov.moh.searchservice.repository.SearchRepository
import zm.gov.moh.searchservice.utils.Fingerprint
import java.util.*

@Service
class SearchService(
    @Autowired
    private val searchRepository: SearchRepository
) {
    fun findSubject(subjectId: UUID): Mono<Subject> {
        val subject = searchRepository.findSubjectBySubjectId(subjectId)

        return Mono.just(subject)
    }

    fun findFingerprint(searchPayload: SearchPayload): Mono<FingerprintDao> {
        val probeData = searchPayload.image
        val srcSystemId = searchPayload.sourceSystemId

        val fingerprintFlux = searchRepository.findFingerprintBySrcSystemId(srcSystemId)

        val fingerprint = fingerprintFlux.filter { fingerprintDao ->
            fingerprintDao.data.any { Fingerprint.compareFingerprints(probeData, it.image) }
        }.next()

        return fingerprint
    }
}