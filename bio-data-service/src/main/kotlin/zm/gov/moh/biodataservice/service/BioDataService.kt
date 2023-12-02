package zm.gov.moh.biodataservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.model.FingerprintDao
import zm.gov.moh.biodataservice.repository.BioDataRepository
import java.util.UUID

@Service
class BioDataService(
    @Autowired
    val repository: BioDataRepository
) {
    fun add(data: FingerprintDao): Mono<Boolean> {
        return repository.save(data)
    }

    fun update(data: FingerprintDao): Mono<Boolean> {
        return repository.save(data)
    }

    fun get(subjectId: UUID): Mono<FingerprintDao>? {
        return repository.findById(subjectId)
    }

    fun getAll(): Flux<FingerprintDao> {
        return repository.findAll()
    }

    fun remove(subjectId: UUID): Mono<Boolean> {
        return repository.delete(subjectId)
    }
}