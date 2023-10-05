package zm.gov.moh.enrolmentservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import zm.gov.moh.enrolmentservice.client.SearchClient
import zm.gov.moh.enrolmentservice.model.Subject
import zm.gov.moh.enrolmentservice.repository.EnrolmentRepository
import java.util.UUID

@RestController
@RequestMapping("/enrolments")
class EnrolmentController(
    @Autowired
    private val enrolmentRepository: EnrolmentRepository,
    @Autowired
    private val searchClient: SearchClient
) {
    companion object {
        private val logger = LoggerFactory.getLogger(EnrolmentController::class.java)
    }

    @PostMapping
    fun add(@RequestBody subject: Subject): Subject {
        if (searchClient.search(subject.bioFingerprints) == null) {
            val sub = enrolmentRepository.addEnrolment(subject)
            logger.info("Enrolment add: {}", sub)
            return sub
        }
        throw ResponseStatusException(HttpStatus.CONFLICT, "Submitted finger print(s) data already exists.")
    }

    @GetMapping
    fun findAll(): List<Subject> {
        return enrolmentRepository.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): Subject {
        logger.info("Enrolment find: id={}", id)
        try {
            val subject = enrolmentRepository.findById(UUID.fromString(id))
            subject.bioFingerprints =
                searchClient.getById(id) //TODO: Change this to pull from the biometrics-data service
            return subject
        } catch (e: IllegalArgumentException) {
            logger.error("Enrolment find error", e)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            logger.error("Enrolment find error", e)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

    }
}