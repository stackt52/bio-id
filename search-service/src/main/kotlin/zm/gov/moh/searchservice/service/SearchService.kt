package zm.gov.moh.searchservice.service

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

    fun identifyAny(searchPayload: List<SearchDTO>): Mono<MatchScore> {
        // we could run search in parallel but its really pointless considering that we are running it on async streams
        return Flux.fromIterable(searchPayload)
            .flatMap { i -> identify(i) }
            .take(1)
            .toMono()
    }

    fun identify(searchPayload: FingerprintImageDTO): Mono<SubjectDTO> {
        val probeTemplate = FingerprintTemplate(searchPayload.image)
        val payload = SearchDTO(probeTemplate.toByteArray(), "")

        return identify(payload)
            .flatMap { i -> enrolmentClient.findById(i.subjectId) }
    }

    private fun identify(searchPayload: SearchDTO): Mono<MatchScore> {
        val threshold = 40.0
        val matcher = FingerprintMatcher(FingerprintTemplate(searchPayload.fingerPrintTemplate))

        return bioDataClient.findAll().flatMap { i ->
            Flux.fromIterable(
                i.data.map { d ->
                    SubjectFingerPrintDTO(
                        i.subjectId,
                        FingerprintTemplate(d.fingerPrintTemplate),
                        d.position
                    )
                }
            )
        }.map { i -> MatchScore(i.subjectId, matcher.match(i.fingerprintTemplate)) }
            .filter { i -> i.score >= threshold }
            .singleOrEmpty()
    }
}