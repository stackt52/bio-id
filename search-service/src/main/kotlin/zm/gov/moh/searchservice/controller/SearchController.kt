package zm.gov.moh.searchservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import zm.gov.moh.searchservice.extern.GetBioFingerPrintData
import zm.gov.moh.searchservice.extern.GetClientDetails
import zm.gov.moh.searchservice.model.SearchPayload
import zm.gov.moh.searchservice.model.Subject
import zm.gov.moh.searchservice.service.SearchService

@RestController
@RequestMapping("/search")
class SearchController(
    @Autowired
    private val getBioFingerPrintData: GetBioFingerPrintData,
    @Autowired
    private val getClientDetails: GetClientDetails,
    @Autowired
    private val searchService: SearchService
) {
    @PostMapping
    fun search(@RequestBody searchPayload: SearchPayload): Subject? {
        logger.info("Search: {}", searchPayload)
        try {
            val bioFingerPrintData = getBioFingerPrintData.getAll()

            val probe = searchPayload.fingerPrintTemplate!!

            val clientBioFingerPrintData = searchService.identifyFingerprint(probe, bioFingerPrintData)!!

            val clientUUID = clientBioFingerPrintData.subjectId!!

            return getClientDetails.getByClientUuid(clientUUID)
        } catch (e: Exception) {
            logger.error("search error: ", e)
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }
}