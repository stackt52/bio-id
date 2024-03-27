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
import zm.gov.moh.biodataservice.entity.Fingerprint
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
    fun redisTemplate(connectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisOperations<String, Fingerprint> {
        val serializationContext: RedisSerializationContext<String, Fingerprint> = RedisSerializationContext
            .newSerializationContext<String, Fingerprint>(StringRedisSerializer())
            .key(StringRedisSerializer())
            .value(Jackson2JsonRedisSerializer(Fingerprint::class.java))
            .hashKey(Jackson2JsonRedisSerializer(UUID::class.java))
            .hashValue(Jackson2JsonRedisSerializer(Fingerprint::class.java))
            .build()

        return ReactiveRedisTemplate(connectionFactory, serializationContext)
    }
}