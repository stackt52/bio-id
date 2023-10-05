package zm.gov.moh.searchservice.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zm.gov.moh.searchservice.model.BioFingerPrintData
import zm.gov.moh.searchservice.model.Subject
import zm.gov.moh.searchservice.repository.SearchRepository
import java.util.*

@RestController
@RequestMapping("/search")
class SearchController(
    @Autowired
    private val searchRepository: SearchRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(SearchController::class.java)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): List<BioFingerPrintData> {
        return searchRepository.getById(UUID.fromString(id))
    }

    @PostMapping
    fun search(@RequestBody searchPayload: List<BioFingerPrintData>): Subject? {
        return null
    }
}