package zm.gov.moh.consoleservice.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import zm.gov.moh.consoleservice.client.SearchClient
import zm.gov.moh.consoleservice.model.ClientDTO
import zm.gov.moh.consoleservice.util.ItemNotFoundException

@RestController
@RequestMapping("/console/search")
@Api(tags = ["", "Search"], description = "Console endpoint")
class ConsoleSearchController(
    @Autowired
    private val searchClient: SearchClient
) {
    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
        value = "Find a client using a given fingerprint image",
        response = ClientDTO::class
    )
    fun search(@RequestPart("fingerprint") file: FilePart): Mono<ClientDTO> {
        return searchClient.search(file).onErrorMap { i ->
            ItemNotFoundException("Item not found")
        }
    }
}