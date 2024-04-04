package zm.gov.moh.identityservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class IdentityServiceApplication

fun main(args: Array<String>) {
	runApplication<IdentityServiceApplication>(*args)
}
