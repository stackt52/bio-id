package zm.gov.moh.consoleservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import zm.gov.moh.consoleservice.client.EnrolmentClient
import zm.gov.moh.consoleservice.client.IdentityClient
import zm.gov.moh.consoleservice.client.SearchClient

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
    fun enrolmentWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://enrolment-service")
            .filter(filterFunction)
            .build()
    }

    @Bean
    fun searchWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://search-service")
            .filter(filterFunction)
            .build()
    }

    @Bean
    fun identityWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://identity-service")
            .filter(filterFunction)
            .build()
    }

    /**
     * Create a managed bean for the EnrolmentClient object type using the WebClient
     * bean created.
     */
    @Bean
    fun enrolmentClient(): EnrolmentClient {
        val httpServiceProxyFactory =
            HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(enrolmentWebClient()))
                .build()

        return httpServiceProxyFactory.createClient(EnrolmentClient::class.java)
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

    @Bean
    fun identityClient(): IdentityClient {
        val httpServiceProxyFactory =
            HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(identityWebClient()))
                .build()

        return httpServiceProxyFactory.createClient(IdentityClient::class.java)
    }
}