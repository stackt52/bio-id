package zm.gov.moh.searchservice.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*

@Configuration
@EnableTransactionManagement
class DataSourceConfig {
    private val hikariDataSource: HikariDataSource

    companion object {
        private val logger = LoggerFactory.getLogger(DataSourceConfig::class.java)
    }

    init {
        val hikariProperties = Properties()
        val dataSourceProperties = Properties()


        val hikariConfig = HikariConfig(hikariProperties)
        hikariConfig.dataSourceProperties = dataSourceProperties
        hikariConfig.jdbcUrl = "jdbc:postgresql://localhost:5432/service"
        hikariConfig.username = "postgres"
        hikariConfig.password = "Pass@321"

        hikariDataSource = HikariDataSource(hikariConfig)

        try {
            val isConnectionValid = hikariDataSource.isRunning
            if (isConnectionValid) {
                println("Connected to the database.")
            } else {
                println("Failed to connect to the database.")
            }
        } catch (e: Exception) {
            System.err.println("An error occurred: " + e.message)
        }

        logger.info("Hikari CP - Maximum Pool Size: $hikariDataSource")
    }

    @Bean
    @Primary
    fun dataSource(): HikariDataSource = hikariDataSource
}