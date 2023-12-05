package zm.gov.moh.enrolmentservice.service

import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
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
        @Autowired
        private val auxiliaryRepository: AuxiliaryRepository
) {

    companion object {
        val logger = LoggerFactory.getLogger(EnrolmentController::class.java)
    }

    @Transactional(rollbackOn = [Exception::class])
    fun addSubject(subject: Subject): Subject {
        var enrolledSubject = Subject()
        if (searchClient.search(subject.fingerprintData) == null) {
            enrolledSubject = subjectRepository.save(subject)
            val bioData = FingerprintDao(enrolledSubject.id!!, subject.fingerprintData!!)
            fingerprintClient.create(bioData)
                    .subscribe { result: Boolean? ->
                        if (!result!!) {
                            throw Exception("Error creating biometric data")
                        }
                    }
        } else {
            throw Exception("Client Fingerprints already enrolled")
        }
        return enrolledSubject
    }

    fun findAll(): List<Subject> {
        // Need for object reconstruction
        return subjectRepository.findAll()
    }


    fun findById(id: UUID): Subject {
        logger.info("Enrolment find: id={}", id)
        val subject = subjectRepository.getReferenceById(id)
        logger.info("Found: id={}", id)

//            subject.bioFingerprints =
//                searchClient.getById(id) //TODO: Change this to pull from the biometrics-data service
        return subject

    }

    fun updateById(subject: Subject): Subject {
        // Only update subject data for now
        return subjectRepository.save(subject)
    }

    @Transactional(rollbackOn = [Exception::class])
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