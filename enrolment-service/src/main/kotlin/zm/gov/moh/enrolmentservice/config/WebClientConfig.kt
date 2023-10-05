package zm.gov.moh.enrolmentservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import zm.gov.moh.enrolmentservice.client.SearchClient

@Configuration
class WebClientConfig(
    @Autowired
    private val filterFunction: LoadBalancedExchangeFilterFunction
) {
    @Bean
    fun searchWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://search-service")
            .filter(filterFunction)
            .build()
    }

    @Bean
    fun searchClient(): SearchClient {
        val httpServiceProxyFactory =
            HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(searchWebClient()))
                .build()

        return httpServiceProxyFactory.createClient(SearchClient::class.java)
    }
}