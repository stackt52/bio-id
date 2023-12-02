package zm.gov.moh.biodataservice.repository

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveHashOperations
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.model.FingerprintDao
import zm.gov.moh.biodataservice.util.HashKey
import java.util.UUID


@Repository
class BioDataRepository(
    @Autowired
    private val reactiveOpts: ReactiveRedisOperations<String, FingerprintDao>
) {
    private val hashOperations: ReactiveHashOperations<String, UUID, FingerprintDao> = reactiveOpts.opsForHash()

    companion object {
        private val logger = LoggerFactory.getLogger(BioDataRepository::class.java)
    }

    fun save(data: FingerprintDao): Mono<Boolean> {
        return hashOperations.put(HashKey.FINGER_PRINT_DATA, data.subjectId, data)
    }

    fun findAll(): Flux<FingerprintDao> {
        return hashOperations
            .entries(HashKey.FINGER_PRINT_DATA)
            .flatMap { Mono.just(it.value) }
    }

    fun findById(id: UUID): Mono<FingerprintDao>? {
        return hashOperations.get(HashKey.FINGER_PRINT_DATA, id)
    }

    fun delete(id: UUID): Mono<Boolean> {
        logger.warn("Deleting fingerprint images for client = {}", id)
        val v = hashOperations.remove(HashKey.FINGER_PRINT_DATA, id)
        return v.hasElement()
    }
}