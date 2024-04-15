package zm.gov.moh.identityservice.config

import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator


@Configuration
@EnableR2dbcRepositories
class R2dbcConfig(
        @Autowired
        private val properties: R2dbcConfigurationProperties
) : AbstractR2dbcConfiguration() {

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(
                ResourceDatabasePopulator(ClassPathResource("schema.sql")))
        return initializer
    }

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val ob = ConnectionFactoryBuilder.withUrl(properties.url)
        val op = ob.buildOptions()
                .mutate()
                .option(ConnectionFactoryOptions.USER, properties.username)
                .option(ConnectionFactoryOptions.PASSWORD, properties.password)

        return ConnectionFactoryBuilder.withOptions(op).build()
    }

    @Bean
    fun r2dbcEntityTemplate(connectionFactory: ConnectionFactory): R2dbcEntityTemplate {
        return R2dbcEntityTemplate(connectionFactory)
    }
}