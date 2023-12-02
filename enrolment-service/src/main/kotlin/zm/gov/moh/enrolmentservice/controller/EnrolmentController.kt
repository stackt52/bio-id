package zm.gov.moh.enrolmentservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import zm.gov.moh.enrolmentservice.client.FingerprintClient
import zm.gov.moh.enrolmentservice.client.SearchClient
import zm.gov.moh.enrolmentservice.model.FingerprintDao
import zm.gov.moh.enrolmentservice.model.Subject
import zm.gov.moh.enrolmentservice.repository.EnrolmentRepository
import zm.gov.moh.enrolmentservice.service.EnrolmentService
import java.util.UUID

@RestController
@RequestMapping("/enrolments")
class EnrolmentController(
        @Autowired
        private val enrolmentRepository: EnrolmentRepository,
        @Autowired
        private val searchClient: SearchClient,
        @Autowired
        private val fingerprintClient: FingerprintClient,
        @Autowired
        private val enrolmentService: EnrolmentService
) {
    companion object {
        val logger = LoggerFactory.getLogger(EnrolmentController::class.java)
    }

    @PostMapping
    fun add(@RequestBody subject: Subject): Subject {
        try {
            if (searchClient.search(subject.fingerprintData) == null) {
                val sub = enrolmentService.addSubject(subject)
                logger.info("Enrolment add: {}", sub)
                val bioData = FingerprintDao(sub.id!!, subject.fingerprintData!!)
                val res = fingerprintClient.create(bioData)
                logger.info("BIO DATA FETCHED: $res")
                return sub
            }

            return enrolmentService.addSubject(subject)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }

    }

    @GetMapping
    fun findAll(): List<Subject> {
        try {
            return enrolmentService.findAll()
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): Subject {
        logger.info("Enrolment find: id={}", id)
        try {
            val subject = enrolmentService.findById(id)
            logger.info("Found: id={}", id)

//            subject.bioFingerprints =
//                searchClient.getById(id) //TODO: Change this to pull from the biometrics-data service
            return subject
        } catch (e: IllegalArgumentException) {
            logger.error("Enrolment find error", e)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            logger.error("Enrolment find error", e)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: UUID, @RequestBody subject: Subject): Subject {
        try {
            subject.id = id
            return enrolmentService.updateById(subject)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String) {
        try {
            enrolmentService.deleteById(UUID.fromString(id))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }
}