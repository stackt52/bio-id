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
import zm.gov.moh.searchservice.dto.SearchDTO
import zm.gov.moh.searchservice.model.SearchPayload
import zm.gov.moh.searchservice.service.SearchService

@RestController
@RequestMapping("/search")
class SearchController(
    @Autowired
    private val searchService: SearchService,
) {
    @PostMapping
    fun search(@RequestBody searchPayload: SearchPayload): Mono<SearchDTO> {
        logger.info("Search: {}", searchPayload)

        return searchService.findFingerprint(searchPayload).flatMap {
            searchService.findSubject(it.subjectId)
        }.switchIfEmpty(
            Mono.error(
                ResponseStatusException(HttpStatus.NOT_FOUND, "subject not found")
            )
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }
}

