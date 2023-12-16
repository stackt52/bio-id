package zm.gov.moh.enrolmentservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.r2dbc")
class R2dbcConfigurationProperties {
    lateinit var username: String
    lateinit var password: String
    lateinit var url: String

    override fun toString(): String {
        return "{username: $username, password: $password, url: $url}"
    }
}
