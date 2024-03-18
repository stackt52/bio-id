package zm.gov.moh.searchservice.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
import zm.gov.moh.searchservice.model.FingerprintImageDTO
import zm.gov.moh.searchservice.model.MatchScore
import zm.gov.moh.searchservice.model.SearchDTO
import zm.gov.moh.searchservice.model.SubjectDTO
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
        response = SubjectDTO::class
    )
    fun search(@RequestBody searchPayload: FingerprintImageDTO): Mono<SubjectDTO> {
        logger.info("Search payload: {}", searchPayload)
        return searchService.identify(searchPayload)
    }

    @PostMapping("/any", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
        value = "Find a client using a given collection of fingerprint templates",
        response = MatchScore::class
    )
    fun searchAny(@RequestBody searchPayload: List<SearchDTO>): Mono<MatchScore> {
        logger.info("Search payloads: {}", searchPayload)
        return searchService.identifyAny(searchPayload)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }
}

