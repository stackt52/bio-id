package zm.gov.moh.biodataservice.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.model.FingerprintDTO
import zm.gov.moh.biodataservice.service.BioDataService
import java.util.UUID

@RestController
@RequestMapping("/bio-data")
@Api(tags = ["Bio-data"], description = "Biometrics endpoint")
class BioDataController(
    @Autowired
    private val bioDataService: BioDataService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(BioDataController::class.java)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(
        value = "Retrieve all the fingerprint data",
        response = FingerprintDTO::class,
        responseContainer = "List"
    )
    fun findAll(): Flux<FingerprintDTO> {
        return bioDataService.getAll()
    }

    @GetMapping("/{subjectId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(
        value = "Retrieve the fingerprint data for a single client using id",
        response = FingerprintDTO::class
    )
    fun findById(@PathVariable subjectId: UUID): Mono<FingerprintDTO> {
        return bioDataService.get(subjectId)
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
        value = "Add a client's fingerprint data",
        response = FingerprintDTO::class
    )
    fun create(@RequestBody fingerPrint: FingerprintDTO): Mono<FingerprintDTO> {
        logger.info("Recording fingerprint data = {}", fingerPrint)
        return bioDataService.add(fingerPrint)
    }

    @PutMapping("/{subjectId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
        value = "Update a client's fingerprint data information",
        response = FingerprintDTO::class
    )
    fun update(@RequestBody fingerPrint: FingerprintDTO): Mono<FingerprintDTO> {
        logger.info("Updating fingerprint data ={}", fingerPrint)
        return bioDataService.update(fingerPrint)
    }

    @DeleteMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(
        value = "Delete a client's fingerprint data",
        response = FingerprintDTO::class
    )
    fun delete(@PathVariable subjectId: UUID): Mono<FingerprintDTO> {
        return bioDataService.remove(subjectId)
    }
}