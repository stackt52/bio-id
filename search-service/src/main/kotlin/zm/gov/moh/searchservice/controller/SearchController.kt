package zm.gov.moh.searchservice.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.dto.SearchDTO
import zm.gov.moh.searchservice.model.SearchPayload
import zm.gov.moh.searchservice.service.SearchService

@RestController
@RequestMapping("/search")
@Api(tags = ["Search"], description = "Fingerprint search endpoint")
class SearchController(
        @Autowired
        private val searchService: SearchService,
) {
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
        value = "Find a client using a given fingerprint image",
        response = SearchDTO::class,
        responseContainer = "List",
    )
    fun search(@RequestBody searchPayload: SearchPayload): Mono<SearchDTO> {
        logger.info("Search: {}", searchPayload)

        return searchService.findFingerprint(searchPayload).flatMap {
            searchService.findSubject(it.subjectId)
        }.switchIfEmpty(
                Mono.error(
                        Throwable("No match found")
                )
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }
}

