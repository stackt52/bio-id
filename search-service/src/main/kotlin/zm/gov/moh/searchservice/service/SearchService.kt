package zm.gov.moh.searchservice.service

import com.machinezoo.sourceafis.FingerprintImage
import com.machinezoo.sourceafis.FingerprintMatcher
import com.machinezoo.sourceafis.FingerprintTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.model.FingerprintDao
import zm.gov.moh.searchservice.model.Subject
import zm.gov.moh.searchservice.repository.SearchRepository
import java.util.*

@Service
class SearchService(
    @Autowired
    private val searchRepository: SearchRepository
) {
    fun findSubjectDetails(subjectId: UUID): Mono<Subject> {
        val subject = searchRepository.findSubjectBySubjectId(subjectId)

        return Mono.just(subject)
    }

    fun findSubjectFingerprint(probeData: ByteArray, srcSystemId: String): Mono<FingerprintDao> {
        val fingerprintDaoFlux = searchRepository.findFingerprintDaoBySrcSystemId(srcSystemId)

        return fingerprintDaoFlux.filter { fingerprintDao ->
            fingerprintDao.data.any { compareFingerprint(probeData, it.image) }
        }.next()
    }


    /**
     * Compare two fingerprint images and return a boolean indicating the similarity.
     *
     * @param probeData Byte array representing the fingerprint image data to probe.
     * @param candidateData Byte array representing the fingerprint image data to compare against.
     * @return True if the fingerprints are similar, false otherwise.
     */
    private fun compareFingerprint(probeData: ByteArray, candidateData: ByteArray): Boolean {
        val probeImage = FingerprintImage(probeData)
        val probe = FingerprintTemplate(probeImage)

        val candidateImage = FingerprintImage(candidateData)
        val candidate = FingerprintTemplate(candidateImage)

        val matcher = FingerprintMatcher(probe)

        val similarity = matcher.match(candidate)

        return similarity >= FINGERPRINT_SIMILARITY_THRESHOLD
    }

    companion object {
        // according to sourceAFIS docs 40 corresponds to false match rate 0.01% which is good starting point
        private const val FINGERPRINT_SIMILARITY_THRESHOLD = 40
    }
}