package zm.gov.moh.biodataservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
import org.springframework.data.redis.serializer.StringRedisSerializer
import zm.gov.moh.biodataservice.model.FingerprintDao


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
        val valueSerializer: Jackson2JsonRedisSerializer<FingerprintDao> =
            Jackson2JsonRedisSerializer(FingerprintDao::class.java)

        val builder: RedisSerializationContextBuilder<String, FingerprintDao> =
            RedisSerializationContext.newSerializationContext(StringRedisSerializer())

        val context: RedisSerializationContext<String, FingerprintDao> =
            builder.value(valueSerializer).build()

        return ReactiveRedisTemplate(connectionFactory, context)
    }
}