package zm.gov.moh.searchservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.extern.GetSubject
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
    fun search(@RequestBody searchPayload: SearchPayload): Mono<Subject> {
        logger.info("Search: {}", searchPayload)

        try {
            val probeData = searchPayload.image
            val srcSystemId = searchPayload.sourceSystemId

            val fingerprint = searchService.findSubjectFingerprint(probeData, srcSystemId)

            return fingerprint.flatMap {
                searchService.findSubjectDetails(it.subjectId)
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