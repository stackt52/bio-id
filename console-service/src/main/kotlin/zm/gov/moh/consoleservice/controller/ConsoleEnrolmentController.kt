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
import zm.gov.moh.consoleservice.model.EnrolmentJsonDTO
import zm.gov.moh.consoleservice.model.FingerprintImageDTO
import zm.gov.moh.consoleservice.util.MultipartFileConverter
import zm.gov.moh.consoleservice.util.Position
import java.util.*

@RestController
@RequestMapping("/console/enrolments")
@Api(tags = ["Console", "Enrolment"], description = "Console enrolment endpoint")
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
    fun enrollClient(@ModelAttribute subjectDetails: EnrolmentDTO): Mono<ClientDTO> {
        val enrolmentJsonDTO = multipartToJsonDto(subjectDetails)
        return enrolmentClient.enroll(enrolmentJsonDTO)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
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

    companion object {
        fun multipartToJsonDto(subjectDetails: EnrolmentDTO): EnrolmentJsonDTO {
            val enrolmentJsonDTO = with(subjectDetails) {
                EnrolmentJsonDTO(
                    firstName,
                    middleName,
                    lastName,
                    sex,
                    dateOfBirth,
                    clientIdName,
                    clientIdValue
                )
            }
            val fingerprints = mutableListOf<FingerprintImageDTO>()
            if (subjectDetails.rightThumb !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_THUMB,
                        MultipartFileConverter().convert(subjectDetails.rightThumb).bytes
                    )
                )
            if (subjectDetails.rightIndex !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_INDEX,
                        MultipartFileConverter().convert(subjectDetails.rightIndex).bytes
                    )
                )
            if (subjectDetails.rightMiddle !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_MIDDLE,
                        MultipartFileConverter().convert(subjectDetails.rightMiddle).bytes
                    )
                )
            if (subjectDetails.rightRing !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_RING,
                        MultipartFileConverter().convert(subjectDetails.rightRing).bytes
                    )
                )
            if (subjectDetails.rightPinky !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_PINKY,
                        MultipartFileConverter().convert(subjectDetails.rightPinky).bytes
                    )
                )
            if (subjectDetails.leftThumb !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.LEFT_THUMB,
                        MultipartFileConverter().convert(subjectDetails.leftThumb).bytes
                    )
                )
            if (subjectDetails.leftIndex !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.LEFT_INDEX,
                        MultipartFileConverter().convert(subjectDetails.leftIndex).bytes
                    )
                )
            if (subjectDetails.leftMiddle !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.LEFT_MIDDLE,
                        MultipartFileConverter().convert(subjectDetails.leftMiddle).bytes
                    )
                )
            if (subjectDetails.leftRing !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.LEFT_RING,
                        MultipartFileConverter().convert(subjectDetails.leftRing).bytes
                    )
                )
            if (subjectDetails.leftPinky !== null)
                fingerprints.add(
                    FingerprintImageDTO(
                        Position.LEFT_PINKY,
                        MultipartFileConverter().convert(subjectDetails.leftPinky).bytes
                    )
                )

            enrolmentJsonDTO.fingerprints = fingerprints
            return enrolmentJsonDTO
        }
    }
}