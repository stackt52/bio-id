package zm.gov.moh.enrolmentservice.service

import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.client.FingerprintClient
import zm.gov.moh.enrolmentservice.client.SearchClient
import zm.gov.moh.enrolmentservice.controller.EnrolmentController
import zm.gov.moh.enrolmentservice.model.FingerprintDao
import zm.gov.moh.enrolmentservice.model.Subject
import zm.gov.moh.enrolmentservice.repository.AuxiliaryRepository
import zm.gov.moh.enrolmentservice.repository.SubjectRepository
import java.util.*


@Service
class EnrolmentService(
    @Autowired
    private val searchClient: SearchClient,
    @Autowired
    private val fingerprintClient: FingerprintClient,
    @Autowired
    private val subjectRepository: SubjectRepository,
) {

    companion object {
        val logger = LoggerFactory.getLogger(EnrolmentController::class.java)
    }

    @Transactional
    fun addSubject(subject: Subject): Mono<Subject> {
        if (searchClient.search(subject.fingerprintData) == null) {
            return Mono.just(subjectRepository.save(subject))
                .map { i -> FingerprintDao(i.id!!, subject.fingerprintData!!) }
                .flatMap { i ->
                    fingerprintClient.create(i).doOnNext { v ->
                        if (v) {
                            throw Exception("Error creating biometric data")
                        }
                    }
                }.flatMap { i -> Mono.just(subject) }
        } else {
            throw Exception("Client Fingerprints already enrolled")
        }
    }

    fun findAll(): List<Subject> {
        throw NotImplementedError()
    }

    fun findById(id: UUID): Subject {
        logger.info("Enrolment find: id={}", id)
        val subject = subjectRepository.getReferenceById(id)
        logger.info("Found: id={}", id)
        return subject
    }

    fun updateById(subject: Subject): Subject {
        // Only update subject data for now
        return subjectRepository.save(subject)
    }

    @Transactional
    fun deleteById(id: UUID) {
        subjectRepository.deleteById(id)
        fingerprintClient.delete(id)
            .doOnError {
                throw Exception("Error deleting biometric data")
            }
            .subscribe { result: Boolean? ->
                if (!result!!) {
                    throw Exception("Error deleting biometric data")
                }
            }
    }
}