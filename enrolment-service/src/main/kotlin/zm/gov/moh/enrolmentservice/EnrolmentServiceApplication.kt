package zm.gov.moh.enrolmentservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class EnrolmentServiceApplication

fun main(args: Array<String>) {
	runApplication<EnrolmentServiceApplication>(*args)
}
