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
    suspend fun addSubject(client: ClientDTO): ClientDTO {
        val clientId = UUID.randomUUID()
        val matchScore =
            searchClient.searchAny(client.fingerprintImages).awaitSingleOrNull()

        val subject = with(client) {
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
                .flatMap { i ->
                    client.fingerprintImages.forEach { v -> v.clientId = i.id }
                    bioDataClient.create(
                        client.fingerprintImages
                    )
                }.flatMap { _ ->
                    client.newClient = true
                    Mono.just(client)
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