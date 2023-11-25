package zm.gov.moh.biodataservice.repository

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.model.FingerprintDto
import java.util.UUID


@Repository
class BioDataRepository(
    @Autowired
    private val reactiveTemplate: ReactiveRedisTemplate<String, FingerprintDto>
) {

    companion object {
        private val logger = LoggerFactory.getLogger(BioDataRepository::class.java)
    }

    fun save(data: FingerprintDto): Mono<Boolean> {
        return reactiveTemplate.opsForValue().set(data.subjectId.toString(), data)
    }

    fun findAll(): Flux<FingerprintDto> {
        return reactiveTemplate.keys("*")
            .flatMap(reactiveTemplate.opsForValue()::get);
    }

    fun findById(id: UUID): Mono<FingerprintDto>? {
        return reactiveTemplate.opsForValue().get(id.toString())
    }

    fun delete(id: UUID): Mono<Boolean> {
        return reactiveTemplate.opsForValue().delete(id.toString())
    }
}