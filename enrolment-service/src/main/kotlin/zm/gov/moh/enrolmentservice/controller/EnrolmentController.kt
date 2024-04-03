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
import zm.gov.moh.enrolmentservice.model.ClientDTO
import zm.gov.moh.enrolmentservice.model.EnrolmentDTO
import zm.gov.moh.enrolmentservice.model.FingerprintImageDTO
import zm.gov.moh.enrolmentservice.service.EnrolmentService
import zm.gov.moh.enrolmentservice.util.BadRequestException
import zm.gov.moh.enrolmentservice.util.Position
import java.util.*

@RestController
@RequestMapping("/enrolments")
class EnrolmentController(
    @Autowired
    private val enrolmentService: EnrolmentService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(EnrolmentController::class.java)
    }

    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun add(@ModelAttribute subjectDetails: EnrolmentDTO): ClientDTO {
        val fingerprints = fingerprintImages(subjectDetails)
        if (fingerprints.isEmpty()) {
            // Web specific exceptions should be thrown in the web layer and not in the service layer.
            // Not doing so would tie the service layer to web, which is BAD implementation.
            throw BadRequestException("No fingerprint(s) uploaded.")
        }

        return enrolmentService.enrolClient(subjectDetails, fingerprints)
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
        } catch (e: Exception) {
            logger.error("Error occurred when deleting subject: {}", e.stackTrace)
            throw Throwable(e.message, e.cause)
        }
    }

    private fun fingerprintImages(enrolmentDTO: EnrolmentDTO): List<FingerprintImageDTO> {
        val fingerPrints = mutableListOf<FingerprintImageDTO>()
        if (enrolmentDTO.rightThumb != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_THUMB, enrolmentDTO.rightThumb.bytes))
        }
        if (enrolmentDTO.rightIndex != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_INDEX, enrolmentDTO.rightIndex.bytes))
        }
        if (enrolmentDTO.rightMiddle != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_MIDDLE, enrolmentDTO.rightMiddle.bytes))
        }
        if (enrolmentDTO.rightRing != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_RING, enrolmentDTO.rightRing.bytes))
        }
        if (enrolmentDTO.rightPinky != null) {
            fingerPrints.add(FingerprintImageDTO(Position.RIGHT_PINKY, enrolmentDTO.rightPinky.bytes))
        }
        if (enrolmentDTO.leftThumb != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_THUMB, enrolmentDTO.leftThumb.bytes))
        }
        if (enrolmentDTO.leftIndex != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_INDEX, enrolmentDTO.leftIndex.bytes))
        }
        if (enrolmentDTO.leftMiddle != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_MIDDLE, enrolmentDTO.leftMiddle.bytes))
        }
        if (enrolmentDTO.leftRing != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_RING, enrolmentDTO.leftRing.bytes))
        }
        if (enrolmentDTO.leftPinky != null) {
            fingerPrints.add(FingerprintImageDTO(Position.LEFT_PINKY, enrolmentDTO.leftPinky.bytes))
        }
        return fingerPrints
    }
}