package zm.gov.moh.enrolmentservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.enrolmentservice.model.ClientDTO
import zm.gov.moh.enrolmentservice.model.EnrolmentDTO
import zm.gov.moh.enrolmentservice.model.EnrolmentJsonDTO
import zm.gov.moh.enrolmentservice.model.FingerprintImageDTO
import zm.gov.moh.enrolmentservice.service.EnrolmentService
import zm.gov.moh.enrolmentservice.util.BadRequestException
import zm.gov.moh.enrolmentservice.util.MultipartFileConverter
import zm.gov.moh.enrolmentservice.util.Position
import java.util.*


@RestController
@RequestMapping("/enrolments")
class EnrolmentController(
    @Autowired
    private val enrolmentService: EnrolmentService
) {

    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun enroll(@ModelAttribute subjectDetails: EnrolmentDTO): Mono<ClientDTO> {
        val fingerprints = fingerprintImages(subjectDetails)
        if (fingerprints.isEmpty()) {
            // Web specific exceptions should be thrown in the web layer and not in the service layer.
            // Not doing so would tie the service layer to web, which is BAD implementation.
            throw BadRequestException("No fingerprint(s) uploaded.")
        }

        return enrolmentService.enrolClient(subjectDetails, fingerprints)
    }

    @PostMapping("/json", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun enroll(@RequestBody enrolmentJsonDTO: EnrolmentJsonDTO): Mono<ClientDTO> {
        if (enrolmentJsonDTO.fingerprints.isEmpty()) {
            // Web specific exceptions should be thrown in the web layer and not in the service layer.
            // Not doing so would tie the service layer to web, which is BAD implementation.
            throw BadRequestException("No fingerprint(s) uploaded.")
        }
        val enrolmentDTO = with(enrolmentJsonDTO) {
            EnrolmentDTO(
                firstName,
                middleName,
                lastName,
                sex,
                dateOfBirth,
                clientIdName,
                clientIdValue
            )
        }
        return enrolmentService.enrolClient(enrolmentDTO, enrolmentJsonDTO.fingerprints)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): Flux<ClientDTO> {
        try {
            return enrolmentService.findAll()
        } catch (e: Exception) {
            logger.error("Error occurred when getting subjects: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable id: UUID): Mono<ClientDTO> {
        try {
            return enrolmentService.findById(id)
        } catch (e: Exception) {
            logger.error("Error occurred when retrieving subject with id = {}: {}", id, e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @PutMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun updateById(@PathVariable id: UUID, @RequestBody subject: ClientDTO): Mono<ClientDTO> {
        try {
            subject.id = id
            return enrolmentService.updateById(subject)
        } catch (e: Exception) {
            logger.error("Error occurred when updating subject = {}: {}", subject, e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): Mono<ClientDTO> {
        try {
            return enrolmentService.deleteById(UUID.fromString(id))
                .flatMap { _ -> Mono.empty() }
        } catch (e: Exception) {
            logger.error("Error occurred when deleting subject: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }


    companion object {
        private val logger = LoggerFactory.getLogger(EnrolmentController::class.java)

        private fun fingerprintImages(enrolmentDTO: EnrolmentDTO): List<FingerprintImageDTO> {
            val fingerPrints = mutableListOf<FingerprintImageDTO>()
            if (enrolmentDTO.rightThumb != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_THUMB,
                        MultipartFileConverter().convert(enrolmentDTO.rightThumb).bytes
                    )
                )
            }
            if (enrolmentDTO.rightIndex != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_INDEX,
                        MultipartFileConverter().convert(enrolmentDTO.rightIndex).bytes
                    )
                )
            }
            if (enrolmentDTO.rightMiddle != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_MIDDLE,
                        MultipartFileConverter().convert(enrolmentDTO.rightMiddle).bytes
                    )
                )
            }
            if (enrolmentDTO.rightRing != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_RING,
                        MultipartFileConverter().convert(enrolmentDTO.rightRing).bytes
                    )
                )
            }
            if (enrolmentDTO.rightPinky != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.RIGHT_PINKY,
                        MultipartFileConverter().convert(enrolmentDTO.rightPinky).bytes
                    )
                )
            }
            if (enrolmentDTO.leftThumb != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.LEFT_THUMB,
                        MultipartFileConverter().convert(enrolmentDTO.leftThumb).bytes
                    )
                )
            }
            if (enrolmentDTO.leftIndex != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.LEFT_INDEX,
                        MultipartFileConverter().convert(enrolmentDTO.leftIndex).bytes
                    )
                )
            }
            if (enrolmentDTO.leftMiddle != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.LEFT_MIDDLE,
                        MultipartFileConverter().convert(enrolmentDTO.leftMiddle).bytes
                    )
                )
            }
            if (enrolmentDTO.leftRing != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.LEFT_RING,
                        MultipartFileConverter().convert(enrolmentDTO.leftRing).bytes
                    )
                )
            }
            if (enrolmentDTO.leftPinky != null) {
                fingerPrints.add(
                    FingerprintImageDTO(
                        Position.LEFT_PINKY,
                        MultipartFileConverter().convert(enrolmentDTO.leftPinky).bytes
                    )
                )
            }
            return fingerPrints
        }
    }
}