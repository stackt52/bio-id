package zm.gov.moh.enrolmentservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
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
    fun enrolClient(enrolmentDTO: EnrolmentDTO, fingerprintImages: List<FingerprintImageDTO>): Mono<ClientDTO> {
        val clientId = UUID.randomUUID()

        fingerprintImages.forEach { v -> v.clientId = clientId }

        // Attempt searching fingerprint(s) before enrolling new client
        return searchClient.searchAny(fingerprintImages)
            .flatMap { i ->
                findById(i.subjectId)
            }.switchIfEmpty {
                // if search operation returned no result, enrol client
                val subject = with(enrolmentDTO) {
                    Subject(
                        id = clientId,
                        firstName,
                        middleName,
                        lastName,
                        sex,
                        dateOfBirth
                    )
                }
                template.insert(subject)
                    .flatMap { _ ->
                        bioDataClient.create(
                            fingerprintImages
                        )
                    }.flatMap { _ ->
                        Mono.just(
                            with(subject) {
                                ClientDTO(
                                    id = id,
                                    firstName = firstName,
                                    middleName = middleName,
                                    lastName = lastName,
                                    sex = sex,
                                    dateOfBirth = dateOfBirth,
                                    newClient = true
                                )
                            }
                        )
                    }
            }

//        val matchScore =
//            searchClient.searchAny(fingerprintImages)
//                .awaitSingleOrNull()
//
//        val subject = with(enrolmentDTO) {
//            Subject(
//                id = clientId,
//                firstName,
//                middleName,
//                lastName,
//                sex,
//                dateOfBirth
//            )
//        }
//
//        return if (matchScore == null) {
//            template.insert(subject)
//                .flatMap { _ ->
//                    bioDataClient.create(
//                        fingerprintImages
//                    )
//                }.flatMap { _ ->
//                    Mono.just(
//                        with(subject) {
//                            ClientDTO(
//                                id = id,
//                                firstName = firstName,
//                                middleName = middleName,
//                                lastName = lastName,
//                                sex = sex,
//                                dateOfBirth = dateOfBirth,
//                                newClient = true
//                            )
//                        }
//                    )
//                }.awaitSingle()
//
//        } else {
//            findById(matchScore.subjectId).awaitSingle()
//        }
    }


    fun findAll(): Flux<ClientDTO> {
        return template.select<Subject>().all().map { i ->
            ClientDTO(
                id = i.id,
                firstName = i.firstName,
                middleName = i.middleName,
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
                middleName = i.middleName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }

    fun updateById(subject: ClientDTO): Mono<ClientDTO> {
        // TODO: update also subject's auxiliaryIds, and bioData fingerprints
        val (id, firstName, middleName, lastName, sex, dateOfBirth) = subject
        return template.update(ClientDTO(id, firstName, middleName, lastName, sex, dateOfBirth)).map { i ->
            ClientDTO(
                id = i.id,
                firstName = i.firstName,
                middleName = i.middleName,
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
                middleName = i.middleName,
                lastName = i.lastName,
                sex = i.sex,
                dateOfBirth = i.dateOfBirth
            )
        }
    }
}