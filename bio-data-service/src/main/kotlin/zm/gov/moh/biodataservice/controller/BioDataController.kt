package zm.gov.moh.biodataservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.model.FingerprintDTO
import zm.gov.moh.biodataservice.model.FingerprintImageDTO
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

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): Flux<FingerprintDTO> {
        return bioDataService.getAll()
    }

    @GetMapping("/{subjectId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable subjectId: UUID): Mono<FingerprintDTO> {
        return bioDataService.get(subjectId)
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody fingerPrints: List<FingerprintImageDTO>): Mono<FingerprintDTO> {
        logger.info("Recording fingerprint data = {}", fingerPrints.map { i -> i.position })
        return bioDataService.add(fingerPrints)
    }

    @PutMapping("/{subjectId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody fingerPrint: FingerprintDTO): Mono<FingerprintDTO> {
        logger.info("Updating fingerprint data ={}", fingerPrint.data.map { i -> i.position })
        return bioDataService.update(fingerPrint)
    }

    @DeleteMapping("/{subjectId}")
    fun delete(@PathVariable subjectId: UUID): Mono<FingerprintDTO> {
        return bioDataService.remove(subjectId)
    }
}