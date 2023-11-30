package zm.gov.moh.biodataservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.*
import zm.gov.moh.biodataservice.model.FingerprintDao
import java.util.UUID


@Configuration
class RedisConfig {

    @Value("\${spring.data.redis.host}")
    var hostName: String = ""

    @Value("\${spring.data.redis.port}")
    var port: Int = 0

    @Bean
    @Primary
    fun connectionFactory(): ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory(hostName, port)
    }

    @Bean
    fun redisTemplate(connectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisOperations<String, FingerprintDao> {
        val serializationContext: RedisSerializationContext<String, FingerprintDao> = RedisSerializationContext
            .newSerializationContext<String, FingerprintDao>(StringRedisSerializer())
            .key(StringRedisSerializer())
            .value(Jackson2JsonRedisSerializer(FingerprintDao::class.java))
            .hashKey(Jackson2JsonRedisSerializer(UUID::class.java))
            .hashValue(Jackson2JsonRedisSerializer(FingerprintDao::class.java))
            .build()

        return ReactiveRedisTemplate(connectionFactory, serializationContext)
    }
}