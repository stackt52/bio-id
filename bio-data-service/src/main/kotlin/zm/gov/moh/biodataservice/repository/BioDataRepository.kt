package zm.gov.moh.biodataservice.repository

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.model.FingerprintDao
import java.util.UUID


@Repository
class BioDataRepository(
    @Autowired
    private val reactiveOpts: ReactiveRedisOperations<String, FingerprintDao>
) {

    companion object {
        private val logger = LoggerFactory.getLogger(BioDataRepository::class.java)
    }

    fun save(data: FingerprintDao): Mono<Boolean> {
        return reactiveOpts.opsForValue().set(data.subjectId.toString(), data)
    }

    fun findAll(): Flux<FingerprintDao> {
        return reactiveOpts.scan()
            .flatMap(reactiveOpts.opsForValue()::get)
    }

    fun findById(id: UUID): Mono<FingerprintDao>? {
        return reactiveOpts.opsForValue().get(id.toString())
    }

    fun delete(id: UUID): Mono<Boolean> {
        logger.warn("Deleting fingerprint images for client = {}", id)
        return reactiveOpts.opsForValue().delete(id.toString())
    }
}