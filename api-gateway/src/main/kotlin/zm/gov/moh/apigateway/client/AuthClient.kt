package zm.gov.moh.apigateway.client

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import zm.gov.moh.apigateway.dto.AuthDTO

@HttpExchange
interface AuthClient {
    @PostExchange("/auth/token/validate")
    fun validateToken(@RequestBody authDTO: AuthDTO)
}