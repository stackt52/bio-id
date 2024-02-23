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

    fun save(data: FingerprintDao): Mono<FingerprintDao> {
        return hashOperations
                .put(HashKey.FINGER_PRINT_DATA, data.subjectId, data)
                .flatMap { _ -> Mono.just(data) }
    }

    fun findAll(): Flux<FingerprintDao> {
        return hashOperations
                .entries(HashKey.FINGER_PRINT_DATA)
                .flatMap { Mono.just(it.value) }
    }

    fun findById(id: UUID): Mono<FingerprintDao> {
        return hashOperations.get(HashKey.FINGER_PRINT_DATA, id)
    }

    fun delete(id: UUID): Mono<FingerprintDao> {
        logger.warn("Deleting fingerprint images for client = {}", id)
        return hashOperations.remove(HashKey.FINGER_PRINT_DATA, id)
                .filter { i -> i > 0 }
                .publish { _ -> findById(id) }
    }
}