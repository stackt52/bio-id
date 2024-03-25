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
import zm.gov.moh.searchservice.model.*

@Service
class SearchService(
    @Autowired
    private val bioDataClient: BioDataClient,
) {

    fun identifyAny(searchPayload: List<FingerprintImageDTO>): Mono<MatchScore> {
        /**
         * we could run search in parallel but its really pointless
         * considering that we are running it on async streams.
         */
        return Flux.fromIterable(searchPayload)
            .flatMap { i -> identify(i) }
            .take(1) // receive one value and shutdown the subscription
            .toMono()
    }

    fun identify(fingerprintImage: FingerprintImageDTO): Mono<MatchScore> {
        val threshold = 120.0
        val fImage = FingerprintImage(fingerprintImage.image)
        val fTemplate = FingerprintTemplate(fImage)
        val matcher = FingerprintMatcher(fTemplate) // this is an expensive operation

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
            .filter { i -> i.score >= threshold }
            .singleOrEmpty()
    }
}