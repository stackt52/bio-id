package zm.gov.moh.searchservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import zm.gov.moh.searchservice.model.ClientDTO
import zm.gov.moh.searchservice.model.FingerprintImageDTO
import zm.gov.moh.searchservice.model.MatchScore
import zm.gov.moh.searchservice.service.SearchService
import zm.gov.moh.searchservice.util.ItemNotFoundException
import zm.gov.moh.searchservice.util.MultipartFileConverter


@RestController
@RequestMapping("/search")
class SearchController(
    @Autowired
    private val searchService: SearchService,
) {
    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun search(@RequestPart("fingerprint") file: FilePart): Mono<ClientDTO> {
        logger.info("Searching client using fingerprint...")
        return searchService.searchEnrolledClient(MultipartFileConverter().convert(file).bytes)
            .switchIfEmpty(Mono.error(ItemNotFoundException("No client found.")))
    }

    @PostMapping("/any", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun searchAny(@RequestBody searchPayload: List<FingerprintImageDTO>): Mono<MatchScore> {
        logger.info("Search payloads: {}", searchPayload.map { i -> i.position })
        return searchService.identifyAny(searchPayload)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }
}

