package zm.gov.moh.consoleservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.resource.PathResourceResolver
import org.springframework.web.reactive.resource.ResourceResolverChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Configuration
class WebConfig : WebFluxConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .resourceChain(true)
            .addResolver(IndexFallbackResourceResolver())
    }

    internal class IndexFallbackResourceResolver : PathResourceResolver() {
        override fun resolveResourceInternal(
            exchange: ServerWebExchange?,
            requestPath: String,
            locations: MutableList<out Resource>,
            chain: ResourceResolverChain
        ): Mono<Resource> {
            return super.resolveResourceInternal(exchange, requestPath, locations, chain)
                .switchIfEmpty {
                    super.resolveResourceInternal(exchange, "index.html", locations, chain)
                }
        }
    }
}