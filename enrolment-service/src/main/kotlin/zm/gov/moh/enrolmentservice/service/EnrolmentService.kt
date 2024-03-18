package zm.gov.moh.enrolmentservice.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.client.BioDataClient
import zm.gov.moh.enrolmentservice.client.SearchClient
import zm.gov.moh.enrolmentservice.entity.Subject
import zm.gov.moh.enrolmentservice.model.*
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
    suspend fun addSubject(subject: SubjectDTO): SubjectDTO {
        subject.id = UUID.randomUUID()
        val searchPayloads = subject.fingerprintData.map { i -> SearchDTO(i.fingerPrintTemplate, null) }
        val matchScore =
            searchClient.searchAny(searchPayloads).awaitSingleOrNull()

        return if (matchScore == null) {
            template.insert(subject)
                .flatMap { i ->
                    bioDataClient.create(
                        FingerprintDTO(
                            i.id!!,
                            i.fingerprintData.map { d -> FingerprintDataDTO(d.position, d.fingerPrintTemplate) })
                    )
                }.flatMap { _ ->
                    subject.newClient = true
                    Mono.just(subject)
                }.awaitSingle()

        } else {
            val client = findById(matchScore.subjectId).awaitSingle()
            val (id, firstName, lastName, sex, dateOfBirth) = client
            SubjectDTO(
                id,
                firstName,
                lastName,
                sex,
                dateOfBirth,
                newClient = false
            )
        }
    }

    fun findAll(): Flux<SubjectDTO> {
        return template.select<Subject>().all().map { i ->
            SubjectDTO(
                id = i.id,
                firstName = i.firstName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }

    fun findById(id: UUID): Mono<SubjectDTO> {
        return template.selectOne(query(where("id").`is`(id)), Subject::class.java).map { i ->
            SubjectDTO(
                id = i.id,
                firstName = i.firstName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }

    fun updateById(subject: SubjectDTO): Mono<SubjectDTO> {
        // TODO: update also subject's auxiliaryIds, and bioData fingerprints
        val (id, firstName, lastName, sex, dateOfBirth) = subject
        return template.update(SubjectDTO(id, firstName, lastName, sex, dateOfBirth)).map { i ->
            SubjectDTO(
                id = i.id,
                firstName = i.firstName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }

    fun deleteById(id: UUID): Mono<SubjectDTO> {
        return findById(id).flatMap { i -> template.delete(i) }.map { i ->
            SubjectDTO(
                id = i.id,
                firstName = i.firstName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }
}