package zm.gov.moh.enrolmentservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import zm.gov.moh.enrolmentservice.client.FingerprintClient
import zm.gov.moh.enrolmentservice.client.SearchClient

@Configuration
class WebClientConfig(
    @Autowired
    private val filterFunction: LoadBalancedExchangeFilterFunction
) {
    /**
     * Create a load-balanced WebClient for a service. The service discovery
     * will resolve the instance to call using the service name specified.
     */
    @Bean
    fun searchWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://search-service")
            .filter(filterFunction)
            .build()
    }

    /**
     * Create a managed bean for the SearchClient object type using the WebClient
     * bean created.
     */
    @Bean
    fun searchClient(): SearchClient {
        val httpServiceProxyFactory =
            HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(searchWebClient()))
                .build()

        return httpServiceProxyFactory.createClient(SearchClient::class.java)
    }


    /**
     * Create a load-balanced WebClient for a service. The service discovery
     * will resolve the instance to call using the service name specified.
     */
    @Bean
    fun fingerprintWebClient(): WebClient {
        return WebClient.builder()
                .baseUrl("http://bio-data-service")
                .filter(filterFunction)
                .build()
    }

    /**
     * Create a managed bean for the FingerprintClient object type using the WebClient
     * bean created.
     */
    @Bean
    fun fingerprintClient(): FingerprintClient {
        val httpServiceProxyFactory =
                HttpServiceProxyFactory
                        .builder(WebClientAdapter.forClient(fingerprintWebClient()))
                        .build()

        return httpServiceProxyFactory.createClient(FingerprintClient::class.java)
    }
}