package zm.gov.moh.enrolmentservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.Subject
import zm.gov.moh.enrolmentservice.service.EnrolmentService
import java.util.UUID

@RestController
@RequestMapping("/enrolments")
class EnrolmentController(
        @Autowired
        private val enrolmentService: EnrolmentService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(EnrolmentController::class.java)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody subject: Subject): Mono<Subject> {
        try {
            subject.id = UUID.randomUUID()
            return enrolmentService.addSubject(subject)
        } catch (e: Exception) {
            logger.error("Error occurred when enrolling subject: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @GetMapping
    fun findAll(): Flux<Subject> {
        try {
            return enrolmentService.findAll()
        } catch (e: Exception) {
            logger.error("Error occurred when getting subjects: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): Mono<Subject> {
        try {
            return enrolmentService.findById(id)
        } catch (e: Exception) {
            logger.error("Error occurred when retrieving subject with id = {}: {}", id, e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateById(@PathVariable id: UUID, @RequestBody subject: Subject): Mono<Subject> {
        try {
            subject.id = id
            return enrolmentService.updateById(subject)
        } catch (e: Exception) {
            logger.error("Error occurred when updating subject = {}: {}", subject, e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: String): Mono<Subject> {
        try {
            return enrolmentService.deleteById(UUID.fromString(id))
        } catch (e: Exception) {
            logger.error("Error occurred when deleting subject: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }
}