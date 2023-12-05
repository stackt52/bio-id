package zm.gov.moh.searchservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.model.SearchPayload
import zm.gov.moh.searchservice.model.Subject
import zm.gov.moh.searchservice.service.SearchService

@RestController
@RequestMapping("/search")
class SearchController(
    @Autowired
    private val searchService: SearchService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun search(@RequestBody searchPayload: SearchPayload): Mono<Subject> {
        logger.info("Search: {}", searchPayload)

        try {
            val fingerprint = searchService.findFingerprint(searchPayload)

            return fingerprint.flatMap {
                searchService.findSubject(it.subjectId)
            }

        } catch (e: Exception) {
            logger.error("search error: ", e)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }
}