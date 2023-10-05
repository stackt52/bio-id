package zm.gov.moh.enrolmentservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
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
        val sub = enrolmentRepository.addEnrolment(subject)
        logger.info("Add enrolment: {}", sub)
        return sub
    }

    @GetMapping
    fun findAll(): List<Subject> {
        return enrolmentRepository.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): Subject {
        logger.info("Enrolment find: id={}", id)
        return enrolmentRepository.findById(UUID.fromString(id))
    }
}