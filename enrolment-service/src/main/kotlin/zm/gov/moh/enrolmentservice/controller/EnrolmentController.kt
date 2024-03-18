package zm.gov.moh.enrolmentservice.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.SubjectDTO
import zm.gov.moh.enrolmentservice.service.EnrolmentService
import java.util.UUID

@RestController
@RequestMapping("/enrolments")
@Api(tags = ["Enrolment"], description = "Enrolment endpoint")
class EnrolmentController(
        @Autowired
        private val enrolmentService: EnrolmentService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(EnrolmentController::class.java)
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
        value = "Register a client",
        response = SubjectDTO::class
    )
    suspend fun add(@RequestBody subject: SubjectDTO): SubjectDTO {
        try {
            return enrolmentService.addSubject(subject)
        } catch (e: Exception) {
            logger.error("Error occurred when enrolling subject: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(
        value = "Retrieve all clients",
        response = SubjectDTO::class,
        responseContainer = "List"
    )
    fun findAll(): Flux<SubjectDTO> {
        try {
            return enrolmentService.findAll()
        } catch (e: Exception) {
            logger.error("Error occurred when getting subjects: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(
        value = "Find a client by id",
        response = SubjectDTO::class
    )
    fun findById(@PathVariable id: UUID): Mono<SubjectDTO> {
        try {
            return enrolmentService.findById(id)
        } catch (e: Exception) {
            logger.error("Error occurred when retrieving subject with id = {}: {}", id, e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @PutMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
        value = "Update a client's enrolment information",
        response = SubjectDTO::class
    )
    fun updateById(@PathVariable id: UUID, @RequestBody subject: SubjectDTO): Mono<SubjectDTO> {
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
    @ApiOperation(
        value = "Delete a client",
        response = SubjectDTO::class
    )
    fun deleteById(@PathVariable id: String): Mono<SubjectDTO> {
        try {
            return enrolmentService.deleteById(UUID.fromString(id))
        } catch (e: Exception) {
            logger.error("Error occurred when deleting subject: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }
}