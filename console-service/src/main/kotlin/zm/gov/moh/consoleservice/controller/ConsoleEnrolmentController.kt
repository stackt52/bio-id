package zm.gov.moh.consoleservice.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.consoleservice.client.EnrolmentClient
import zm.gov.moh.consoleservice.model.ClientDTO
import zm.gov.moh.consoleservice.model.EnrolmentDTO
import java.util.*

@RestController
@RequestMapping("/console/enrolments")
@Api(tags = ["Console","Enrolment"], description = "Console enrolment endpoint")
class ConsoleEnrolmentController(
    @Autowired
    private val enrolmentClient: EnrolmentClient
) {

    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
        value = "Register a client",
        response = ClientDTO::class
    )
    suspend fun enrollClient(@ModelAttribute subjectDetails: EnrolmentDTO): ClientDTO {
        return enrolmentClient.add(subjectDetails)
    }

    @GetMapping( produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(
        value = "Retrieve all clients",
        response = ClientDTO::class,
        responseContainer = "List"
    )
    fun findAllEnrolledClients(): Flux<ClientDTO> {
        return enrolmentClient.findAll()
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(
        value = "Find a client by id",
        response = ClientDTO::class
    )
    fun findByEnrolledClientId(@PathVariable id: UUID): Mono<ClientDTO> {
        return enrolmentClient.findById(id)
    }

    @PutMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
        value = "Update a client's enrolment information",
        response = ClientDTO::class
    )
    fun updateEnrolledClientById(@PathVariable id: UUID, @RequestBody subject: ClientDTO): Mono<ClientDTO> {
        return enrolmentClient.updateById(id, subject)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(
        value = "Delete a client",
        response = ClientDTO::class
    )
    fun deleteEnrolledClientById(@PathVariable id: String): Mono<ClientDTO> {
        return enrolmentClient.deleteById(id)
    }
}