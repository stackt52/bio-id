package zm.gov.moh.biodataservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.entity.Fingerprint
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
    fun findAll(): Flux<Fingerprint> {
        return bioDataService.getAll()
    }

    @GetMapping("/{subjectId}")
    fun findById(@PathVariable subjectId: UUID): Mono<Fingerprint> {
        return bioDataService.get(subjectId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody fingerPrint: Fingerprint): Mono<Fingerprint> {
        logger.info("Recording fingerprint data = {}", fingerPrint)
        return bioDataService.add(fingerPrint)
    }

    @PutMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody fingerPrint: Fingerprint): Mono<Fingerprint> {
        logger.info("Updating fingerprint data ={}", fingerPrint)
        return bioDataService.update(fingerPrint)
    }

    @DeleteMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable subjectId: UUID): Mono<Fingerprint> {
        return bioDataService.remove(subjectId)
    }
}