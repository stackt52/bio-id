package zm.gov.moh.biodataservice.model

import java.util.UUID


data class FingerprintDTO(
    val subjectId: UUID = UUID.randomUUID(),
    val data: List<FingerprintDataDTO> = mutableListOf(),
    val srcSystemId: String = ""
)