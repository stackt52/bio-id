package zm.gov.moh.searchservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import zm.gov.moh.searchservice.extern.GetSubject
import zm.gov.moh.searchservice.extern.GetFingerprintData

@Configuration
class WebClientConfig(
    @Autowired
    private val filterFunction: LoadBalancedExchangeFilterFunction
) {
    @Bean
    fun bioDataWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://bio-data-service")
            .filter(filterFunction)
            .build()
    }

    @Bean
    fun enrolmentWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://enrolment-service")
            .filter(filterFunction)
            .build()
    }

    @Bean
    fun getBioFingerPrintData(): GetFingerprintData {
        val httpServiceProxyFunction = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(bioDataWebClient()))
            .build()

        return httpServiceProxyFunction.createClient(GetFingerprintData::class.java)
    }

    @Bean
    fun getClientDetails(): GetSubject {
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(enrolmentWebClient()))
            .build()

        return httpServiceProxyFactory.createClient(GetSubject::class.java)
    }
}