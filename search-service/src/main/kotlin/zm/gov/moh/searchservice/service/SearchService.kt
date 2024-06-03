package zm.gov.moh.searchservice.service

import com.machinezoo.sourceafis.FingerprintImage
import com.machinezoo.sourceafis.FingerprintMatcher
import com.machinezoo.sourceafis.FingerprintTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import zm.gov.moh.searchservice.client.BioDataClient
import zm.gov.moh.searchservice.client.EnrolmentClient
import zm.gov.moh.searchservice.model.*

@Service
class SearchService(
    @Autowired
    private val bioDataClient: BioDataClient,
    @Autowired
    private val enrolmentClient: EnrolmentClient
) {

    fun searchEnrolledClient(image: ByteArray): Mono<ClientDTO> {
        return identify(image).flatMap { i ->
            enrolmentClient.findById(i.subjectId)
        }
    }

    fun identifyAny(searchPayload: List<FingerprintImageDTO>): Mono<MatchScore> {
        /**
         * we could run search in parallel but its really pointless
         * considering that we are running it on async streams.
         */
        return Flux.fromIterable(searchPayload)
            .flatMap { i -> identify(i.image) }
            .take(1) // receive one value and shutdown the subscription
            .toMono()
    }

    private fun identify(image: ByteArray): Mono<MatchScore> {
        val fImage = FingerprintImage(image)
        val fTemplate = FingerprintTemplate(fImage)
        val matcher = FingerprintMatcher(fTemplate) // this is an expensive operation. Use with caution!

        return bioDataClient.findAll().flatMap { i ->
            Flux.fromIterable(
                i.data.map { d ->
                    SubjectFingerPrintDTO(
                        i.subjectId,
                        FingerprintTemplate(d.serializedFingerprintTemplate),
                        d.position
                    )
                }
            )
        }.map { i -> MatchScore(i.subjectId, matcher.match(i.fingerprintTemplate)) }
            .filter { i -> i.score >= FINGERPRINT_THRESHOLD }
            .singleOrEmpty()
    }

    companion object {
        private const val FINGERPRINT_THRESHOLD = 120.0
    }
}