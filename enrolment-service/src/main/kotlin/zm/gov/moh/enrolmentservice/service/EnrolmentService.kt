package zm.gov.moh.enrolmentservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.client.FingerprintClient
import zm.gov.moh.enrolmentservice.client.SearchClient
import zm.gov.moh.enrolmentservice.model.FingerprintDao
import zm.gov.moh.enrolmentservice.model.Subject
import java.util.*


@Service
class EnrolmentService(
        @Autowired
        private val searchClient: SearchClient,
        @Autowired
        private val fingerprintClient: FingerprintClient,
        @Autowired
        private val template: R2dbcEntityTemplate,
) {
    fun addSubject(subject: Subject): Mono<Subject> {
        // Before enrolling new client perform a search to determine if fingerprint data already exists.
        // only attempt registering new client when research returns Mono.empty().
        return searchClient.search(subject.fingerprintData)
                .hasElement()
                .filter { i -> !i } // move to the next operator only if search returned empty
                .flatMap { _ ->
                    template.insert(subject)
                            .flatMap { i -> fingerprintClient.create(FingerprintDao(i.id, i.fingerprintData)) }
                            .flatMap { _ -> Mono.just(subject) }
                }
    }

    fun findAll(): Flux<Subject> {
        return template.select<Subject>().all()
    }

    fun findById(id: UUID): Mono<Subject> {
        return template.selectOne(query(where("id").`is`(id)), Subject::class.java)
    }

    fun updateById(subject: Subject): Mono<Subject> {
        return template.update(subject)
    }

    fun deleteById(id: UUID): Mono<Subject> {
        return findById(id).flatMap { i -> template.delete(i) }
    }
}