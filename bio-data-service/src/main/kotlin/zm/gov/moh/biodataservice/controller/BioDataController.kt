package zm.gov.moh.biodataservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.model.FingerprintData
import zm.gov.moh.biodataservice.model.FingerprintDto
import zm.gov.moh.biodataservice.service.BioDataService
import java.util.UUID

@RestController
@RequestMapping("/bio-data")
class BioDataController(
    @Autowired
    private val bioDataService: BioDataService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(BioDataController::class.java)
    }

    @GetMapping
    fun findAll(): Flux<FingerprintDto> {
        return bioDataService.getAll()
    }

    @GetMapping("/{subjectId}")
    fun findById(@PathVariable subjectId: UUID): Mono<FingerprintDto>? {
        return bioDataService.get(subjectId)
    }

    @GetMapping("/src-system/{sourceSystemId}")
    fun findBySrcSystemId(@PathVariable sourceSystemId: UUID): List<FingerprintData>? {
        return null
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody fingerPrint: FingerprintDto): Mono<Boolean> {
        logger.info("Recording fingerprint data = {}", fingerPrint)
        return bioDataService.add(fingerPrint)
    }

    @PutMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable subjectId: UUID, @RequestBody fingerPrint: FingerprintDto): Mono<Boolean> {
        bioDataService.remove(subjectId)
        return bioDataService.add(fingerPrint)
    }

    @DeleteMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable subjectId: UUID) {
        bioDataService.remove(subjectId)
    }
}