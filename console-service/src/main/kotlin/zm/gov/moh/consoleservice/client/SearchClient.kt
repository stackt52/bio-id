package zm.gov.moh.consoleservice.client

import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Mono
import zm.gov.moh.consoleservice.model.ClientDTO

@HttpExchange
interface SearchClient {
    @PostExchange("/search")
    fun search(@RequestPart("fingerprint") file: FilePart): Mono<ClientDTO>
}