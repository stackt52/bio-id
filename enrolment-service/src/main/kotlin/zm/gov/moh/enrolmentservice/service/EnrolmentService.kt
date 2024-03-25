package zm.gov.moh.enrolmentservice.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Service
import org.springframework.web.bind.MissingRequestValueException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.client.BioDataClient
import zm.gov.moh.enrolmentservice.client.SearchClient
import zm.gov.moh.enrolmentservice.entity.Subject
import zm.gov.moh.enrolmentservice.model.*
import zm.gov.moh.enrolmentservice.util.Position
import java.util.*

@Service
class EnrolmentService(
    @Autowired
    private val searchClient: SearchClient,
    @Autowired
    private val bioDataClient: BioDataClient,
    @Autowired
    private val template: R2dbcEntityTemplate,
) {
    suspend fun addSubject(enrolmentDTO: EnrolmentDTO): ClientDTO {
        val clientId = UUID.randomUUID()
        val fingerprintImages = fingerprintImages(enrolmentDTO)

        if (fingerprintImages.isEmpty())
            throw MissingRequestValueException("No valid fingerprint image found.")

        fingerprintImages.forEach { v -> v.clientId = clientId }

        val matchScore =
            searchClient.searchAny(fingerprintImages).awaitSingleOrNull()

        val subject = with(enrolmentDTO) {
            Subject(
                id = clientId,
                firstName,
                lastName,
                sex,
                dateOfBirth
            )
        }

        return if (matchScore == null) {
            template.insert(subject)
                .flatMap { _ ->
                    bioDataClient.create(
                        fingerprintImages
                    )
                }.flatMap { _ ->
                    Mono.just(
                        with(subject) {
                            ClientDTO(
                                firstName = firstName,
                                lastName = lastName,
                                sex = sex,
                                dateOfBirth = dateOfBirth,
                                newClient = true
                            )
                        }
                    )
                }.awaitSingle()

        } else {
            val v = findById(matchScore.subjectId).awaitSingle()
            val (id, firstName, lastName, sex, dateOfBirth) = v
            ClientDTO(
                id,
                firstName,
                lastName,
                sex,
                dateOfBirth,
                newClient = false
            )
        }
    }

    private fun fingerprintImages(enrolmentDTO: EnrolmentDTO): List<FingerprintImageDTO> {
        val fingerPrints = mutableListOf<FingerprintImageDTO>()
        if (enrolmentDTO.rightThumb != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_THUMB, enrolmentDTO.rightThumb.bytes))
        }
        if (enrolmentDTO.rightIndex != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_INDEX, enrolmentDTO.rightIndex.bytes))
        }
        if (enrolmentDTO.rightMiddle != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_MIDDLE, enrolmentDTO.rightMiddle.bytes))
        }
        if (enrolmentDTO.rightRing != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_RING, enrolmentDTO.rightRing.bytes))
        }
        if (enrolmentDTO.rightPinky != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_PINKY, enrolmentDTO.rightPinky.bytes))
        }
        if (enrolmentDTO.leftThumb != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_THUMB, enrolmentDTO.leftThumb.bytes))
        }
        if (enrolmentDTO.leftIndex != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_INDEX, enrolmentDTO.leftIndex.bytes))
        }
        if (enrolmentDTO.leftMiddle != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_MIDDLE, enrolmentDTO.leftMiddle.bytes))
        }
        if (enrolmentDTO.leftRing != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_RING, enrolmentDTO.leftRing.bytes))
        }
        if (enrolmentDTO.leftPinky != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_PINKY, enrolmentDTO.leftPinky.bytes))
        }
        return fingerPrints
    }

    fun findAll(): Flux<ClientDTO> {
        return template.select<Subject>().all().map { i ->
            ClientDTO(
                id = i.id,
                firstName = i.firstName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }

    fun findById(id: UUID): Mono<ClientDTO> {
        return template.selectOne(query(where("id").`is`(id)), Subject::class.java).map { i ->
            ClientDTO(
                id = i.id,
                firstName = i.firstName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }

    fun updateById(subject: ClientDTO): Mono<ClientDTO> {
        // TODO: update also subject's auxiliaryIds, and bioData fingerprints
        val (id, firstName, lastName, sex, dateOfBirth) = subject
        return template.update(ClientDTO(id, firstName, lastName, sex, dateOfBirth)).map { i ->
            ClientDTO(
                id = i.id,
                firstName = i.firstName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }

    fun deleteById(id: UUID): Mono<ClientDTO> {
        return findById(id).flatMap { i -> template.delete(i) }.map { i ->
            ClientDTO(
                id = i.id,
                firstName = i.firstName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }
}