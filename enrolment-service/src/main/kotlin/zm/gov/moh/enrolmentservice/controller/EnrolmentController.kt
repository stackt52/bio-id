package zm.gov.moh.enrolmentservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.FingerprintDao
import zm.gov.moh.enrolmentservice.model.Subject
import zm.gov.moh.enrolmentservice.service.EnrolmentService
import java.util.UUID

@RestController
@RequestMapping("/enrolments")
class EnrolmentController(
        @Autowired
        private val enrolmentService: EnrolmentService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody subject: Subject): Mono<Subject> {
        try {
            subject.id = UUID.randomUUID()
            return enrolmentService.addSubject(subject)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Error occurred during transaction", e)
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
        try {
            return enrolmentService.findById(id)
        } catch (e: IllegalArgumentException) {
            EnrolmentService.logger.error("Enrolment find error", e)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            EnrolmentService.logger.error("Enrolment find error", e)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateById(@PathVariable id: UUID, @RequestBody subject: Subject): Subject {
        try {
            subject.id = id
            return enrolmentService.updateById(subject)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: String) {
        try {
            enrolmentService.deleteById(UUID.fromString(id))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Error occurred during transaction", e)
        }
    }
}