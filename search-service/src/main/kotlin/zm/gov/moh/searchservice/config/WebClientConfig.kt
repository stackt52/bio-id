package zm.gov.moh.searchservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import zm.gov.moh.searchservice.extern.GetBioFingerPrintData
import zm.gov.moh.searchservice.extern.GetClientDetails

@Configuration
class WebClientConfig(
    @Autowired
    private val filterFunction: LoadBalancedExchangeFilterFunction
) {
    @Bean
    fun biometricDataWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://biometric-data-service")
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
    fun getBioFingerPrintData(): GetBioFingerPrintData {
        val httpServiceProxyFunction = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(biometricDataWebClient()))
            .build()

        return httpServiceProxyFunction.createClient(GetBioFingerPrintData::class.java)
    }

    @Bean
    fun getClientDetails(): GetClientDetails {
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(enrolmentWebClient()))
            .build()

        return httpServiceProxyFactory.createClient(GetClientDetails::class.java)
    }
}