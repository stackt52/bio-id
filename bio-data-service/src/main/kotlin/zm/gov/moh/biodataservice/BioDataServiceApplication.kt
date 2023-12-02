package zm.gov.moh.biodataservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class BioDataServiceApplication

fun main(args: Array<String>) {
	runApplication<BioDataServiceApplication>(*args)
}
