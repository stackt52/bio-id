package zm.gov.moh.consoleservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.format.FormatterRegistry
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.resource.PathResourceResolver
import org.springframework.web.reactive.resource.ResourceResolverChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import zm.gov.moh.consoleservice.util.MultipartFileConverter


@Configuration
class WebConfig : WebFluxConfigurer {

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().maxInMemorySize(3000 * 1024) // 3MB
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
            )
            .allowedHeaders(
                "Access-Control-Allow-Origin",
                "Authorization",
                "Content-Type"
            )
    }

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

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(MultipartFileConverter())
    }
}