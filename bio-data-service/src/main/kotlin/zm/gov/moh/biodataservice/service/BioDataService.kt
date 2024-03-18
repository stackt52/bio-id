package zm.gov.moh.biodataservice.service

import com.machinezoo.sourceafis.FingerprintImage
import com.machinezoo.sourceafis.FingerprintTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import zm.gov.moh.biodataservice.entity.Fingerprint
import zm.gov.moh.biodataservice.entity.FingerprintData
import zm.gov.moh.biodataservice.model.FingerprintDTO
import zm.gov.moh.biodataservice.model.FingerprintDataDTO
import zm.gov.moh.biodataservice.model.FingerprintImageDTO
import zm.gov.moh.biodataservice.repository.BioDataRepository
import java.util.UUID

@Service
class BioDataService(
    @Autowired
    val repository: BioDataRepository
) {
    fun add(data: List<FingerprintImageDTO>): Mono<FingerprintDTO> {
        return repository.save(
            Fingerprint(
                data.first().clientId,
                data.map { d ->
                    FingerprintData(
                        d.position,
                        FingerprintTemplate(
                            FingerprintImage(d.image)
                        ).toByteArray()
                    )
                }
            )
        ).map { f ->
            FingerprintDTO(
                f.subjectId,
                f.data.map { d -> FingerprintDataDTO(d.pos, d.fingerPrintTemplate) })
        }
    }

    fun update(data: FingerprintDTO): Mono<FingerprintDTO> {
        return repository.save(
            Fingerprint(
                data.subjectId,
                data.data.map { d -> FingerprintData(d.position, d.fingerPrintTemplate) })
        ).map { f ->
            FingerprintDTO(
                f.subjectId,
                f.data.map { d -> FingerprintDataDTO(d.pos, d.fingerPrintTemplate) })
        }
    }

    fun get(subjectId: UUID): Mono<FingerprintDTO> {
        return repository.findById(subjectId).map { f ->
            FingerprintDTO(
                f.subjectId,
                f.data.map { d -> FingerprintDataDTO(d.pos, d.fingerPrintTemplate) })
        }
    }

    fun getAll(): Flux<FingerprintDTO> {
        return repository.findAll().map { f ->
            FingerprintDTO(
                f.subjectId,
                f.data.map { d -> FingerprintDataDTO(d.pos, d.fingerPrintTemplate) })
        }
    }

    fun remove(subjectId: UUID): Mono<FingerprintDTO> {
        return repository.delete(subjectId).map { f ->
            FingerprintDTO(
                f.subjectId,
                f.data.map { d -> FingerprintDataDTO(d.pos, d.fingerPrintTemplate) })
        }
    }
}