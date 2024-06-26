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
import zm.gov.moh.enrolmentservice.entity.AuxiliaryId
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
    fun enrolClient(
        enrolmentDTO: EnrolmentDTO,
        fingerprintImages: List<FingerprintImageDTO>
    ): Mono<ClientDTO> {
        val clientId = UUID.randomUUID()

        fingerprintImages.forEach { v -> v.clientId = clientId }

        // Attempt searching fingerprint(s) before enrolling new client
        return searchClient.searchAny(fingerprintImages)
            .flatMap { i ->
                findClientById(i.subjectId)
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
                        createClientDto(subject)
                    }
            }
    }

    fun findAllClients(): Flux<ClientDTO> {
        return template.select<Subject>().all().flatMap { i ->
            createClientDto(i)
        }
    }

    fun findClientById(id: UUID): Mono<ClientDTO> {
        return template.selectOne(query(where("id").`is`(id)), Subject::class.java).flatMap { i ->
            createClientDto(i)
        }
    }

    fun deleteClientById(id: UUID): Mono<ClientDTO> {
        return findClientById(id).flatMap { i -> template.delete(i) }.map { i ->
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

    private fun createClientDto(i: Subject): Mono<ClientDTO> {
        val dto = ClientDTO(
            id = i.id,
            firstName = i.firstName,
            middleName = i.middleName,
            lastName = i.lastName,
            sex = i.sex,
            dateOfBirth = i.dateOfBirth
        )
        return findClientsAuxiliaryIds(i.id).collectList().flatMap { aux ->
            dto.auxiliaryIds = aux
            Mono.just(dto)
        }
    }

    fun findClientsAuxiliaryIds(clientId: UUID): Flux<AuxiliaryIdDTO> {
        return template.select(
            query(where("subjectId").`is`(clientId)),
            AuxiliaryId::class.java
        ).map { i ->
            AuxiliaryIdDTO(i.id, i.type, i.value, i.sourceSystemId)
        }
    }

    fun findAuxiliaryIds(clientId: UUID, type: String, value: String, srcSystemId: UUID): Mono<AuxiliaryId> {
        return template.selectOne(
            query(
                where("subjectId").`is`(clientId)
                    .and("type").`is`(type)
                    .and("value").`is`(value)
                    .and("sourceSystemId").`is`(srcSystemId)
            ), AuxiliaryId::class.java
        )
    }

    fun updateAuxiliaryId(auxiliaryId: AuxiliaryId): Mono<AuxiliaryId> {
        return template.update(auxiliaryId)
    }

    fun saveAuxiliaryId(clientId: UUID, entity: AuxiliaryId): Mono<AuxiliaryId> {
        return template.insert(entity)
    }

    fun updateAuxiliaryIdsById(clientId: UUID, auxiliaryIdDTOs: List<AuxiliaryIdDTO>): Mono<ClientDTO> {
        val auxiliaryIds = auxiliaryIdDTOs.map { i ->
            with(i) {
                AuxiliaryId(
                    0,
                    type = type,
                    value = value,
                    subjectId = clientId,
                    sourceSystemId = sourceSystemId!!
                )
            }
        }

        // Perform an upset operation
        return Flux.fromIterable(auxiliaryIds).flatMap { i ->
            findClientById(clientId).flatMap { _ ->
                findAuxiliaryIds(clientId, i.type, i.value, i.sourceSystemId)
            }.flatMap { a -> updateAuxiliaryId(a) }
                .switchIfEmpty { saveAuxiliaryId(clientId, i) }
        }.flatMap { i -> findClientById(i.subjectId) }
            .singleOrEmpty()
    }
}