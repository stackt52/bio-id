package zm.gov.moh.apigateway.filter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import zm.gov.moh.apigateway.util.JwtUtil
import zm.gov.moh.apigateway.util.UnauthorizedRequestException

@Service
class AuthenticationFilter(
    @Autowired
    private val routeValidator: RouteValidator,
) : AbstractGatewayFilterFactory<AuthenticationFilter.Companion.Config>(Config::class.java) {
    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            if (routeValidator.isSecured.test(exchange.request)) {
                // check if request contain auth token
                if (!exchange.request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw UnauthorizedRequestException("Missing authorization header")
                }
                val authHeader = exchange.request.headers[HttpHeaders.AUTHORIZATION]!!.first()
                if (authHeader.startsWith("Bearer ")) {
                    // We could call the auth service web client and let it handle
                    // token verification, but that wouldn't be performant
                    // because of network call dependence that's why we are relying
                    // util service within the API Gateway for token verification.
                    val token = authHeader.replace("Bearer", "").trim()
                    JwtUtil.validateToken(token)
                }
            }
            chain.filter(exchange)
        }
    }

    companion object {
        class Config
    }
}