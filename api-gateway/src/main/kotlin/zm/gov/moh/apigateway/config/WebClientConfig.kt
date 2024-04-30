package zm.gov.moh.apigateway.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import zm.gov.moh.apigateway.client.AuthClient

@Configuration
class WebClientConfig(
    @Autowired
    private val filterFunction: LoadBalancedExchangeFilterFunction
) {

    @Bean
    fun authWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://identity-service")
            .filter(filterFunction)
            .build()
    }

    @Bean
    fun authClient(): AuthClient {
        val httpServiceProxyFactory =
            HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(authWebClient()))
                .build()

        return httpServiceProxyFactory.createClient(AuthClient::class.java)
    }
}