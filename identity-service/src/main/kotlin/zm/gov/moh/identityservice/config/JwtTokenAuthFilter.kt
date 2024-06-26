package zm.gov.moh.identityservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import zm.gov.moh.identityservice.service.AuthService
import zm.gov.moh.identityservice.service.JwtService

@Component
class JwtTokenAuthFilter(
    @Autowired
    private val authService: AuthService,
    @Autowired
    private val jwtService: JwtService
) : WebFilter {

    companion object {
        private const val HEADER_PREFIX = "Bearer "
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = resolveToken(exchange.request)
        if (!token.isNullOrEmpty() && authService.validateToken(token)) {
            return Mono.fromCallable { jwtService.getAuthentication(token) }
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap { i ->
                    chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(i))
                }
        }
        return chain.filter(exchange)
    }

    private fun resolveToken(request: ServerHttpRequest): String? {
        val bearerToken = request.headers[HttpHeaders.AUTHORIZATION]?.first()
        if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7)
        }
        return null
    }
}