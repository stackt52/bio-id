package zm.gov.moh.biodataservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.entity.Fingerprint
import zm.gov.moh.biodataservice.repository.BioDataRepository
import java.util.UUID

@Service
class BioDataService(
    @Autowired
    val repository: BioDataRepository
) {
    fun add(data: Fingerprint): Mono<Fingerprint> {
        return repository.save(data)
    }

    fun update(data: Fingerprint): Mono<Fingerprint> {
        return repository.save(data)
    }

    fun get(subjectId: UUID): Mono<Fingerprint> {
        return repository.findById(subjectId)
    }

    fun getAll(): Flux<Fingerprint> {
        return repository.findAll()
    }

    fun remove(subjectId: UUID): Mono<Fingerprint> {
        return repository.delete(subjectId)
    }
}