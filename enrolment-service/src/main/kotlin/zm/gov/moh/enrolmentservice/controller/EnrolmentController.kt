package zm.gov.moh.enrolmentservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import zm.gov.moh.enrolmentservice.client.SearchClient
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
        private val enrolmentService: EnrolmentService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(EnrolmentController::class.java)
    }

    @PostMapping
    fun add(@RequestBody subject: Subject): Subject {
        logger.info("Enrolment add: {}", subject)

        return enrolmentService.addSubject(subject)
        // TO BE USED WHEN SEARCH SERVICE IS WORKING
//        if (searchClient.search(subject.bioFingerprints) == null) {
//            val sub = enrolmentService.addSubject(subject)
//            logger.info("Enrolment add: {}", sub)
//            return sub
//        }
//        throw ResponseStatusException(HttpStatus.CONFLICT, "Submitted finger print(s) data already exists.")
    }

    @GetMapping
    fun findAll(): List<Subject> {
        return enrolmentService.findAll()
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

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String) {
        try {
//            val subject = enrolClient.findById(UUID.fromString(id))
//            enrolClient.delete(subject)
        } catch (e: IllegalArgumentException){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }
}