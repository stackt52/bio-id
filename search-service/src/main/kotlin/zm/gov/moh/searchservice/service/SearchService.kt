package zm.gov.moh.searchservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.dto.SearchDTO
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
    fun findSubject(subjectId: UUID): Mono<SearchDTO> {
        val subject = searchRepository.findSubjectBySubjectId(subjectId)

        val searchDTO = toSearchDTO(subject)

        return Mono.just(searchDTO)
    }

    fun findFingerprint(searchPayload: SearchPayload): Mono<FingerprintDao> {
        val probeData = searchPayload.image
        val srcSystemId = searchPayload.sourceSystemId

        val fingerprint = searchRepository.findFingerprintBySrcSystemId(srcSystemId)
            .filter { fingerprintDao ->
                fingerprintDao.data.any { Fingerprint.compareFingerprints(probeData, it.image) }
        }.next()

        return fingerprint
    }

    private fun toSearchDTO(subject: Subject): SearchDTO {
       val searchDTO = SearchDTO()

        searchDTO.id = subject.id
        searchDTO.firstName = subject.firstName
        searchDTO.lastName = subject.lastName
        searchDTO.sex = subject.sex
        searchDTO.dateOfBirth = subject.dateOfBirth
        searchDTO.auxiliaryIds = searchDTO.auxiliaryIds

        return searchDTO
    }
}