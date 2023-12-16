package zm.gov.moh.searchservice.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.model.BioFingerPrintData
import zm.gov.moh.searchservice.model.Subject

@RestController
@RequestMapping("/search")
class SearchController  {

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }

    @PostMapping
    fun search(@RequestBody searchPayload: List<BioFingerPrintData>): Mono<Subject> {
        logger.info("Search: {}", searchPayload)
        return Mono.empty()
    }
}