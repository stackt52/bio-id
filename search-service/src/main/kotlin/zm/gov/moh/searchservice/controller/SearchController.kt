package zm.gov.moh.searchservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import zm.gov.moh.searchservice.model.SearchPayload
import zm.gov.moh.searchservice.model.Subject
import zm.gov.moh.searchservice.service.SearchService

@RestController
@RequestMapping("/search")
class SearchController(
    @Autowired
    private val searchService: SearchService
) {
    @PostMapping
    fun search(@RequestBody searchPayload: SearchPayload): Subject? {
        logger.info("Search: {}", searchPayload)

        try {
            val probe = searchPayload.probe!!

            return searchService.findClientDetails(probe) ?: throw Error("client details not found")
        } catch (e: Exception) {
            logger.error("search error: ", e)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }
}