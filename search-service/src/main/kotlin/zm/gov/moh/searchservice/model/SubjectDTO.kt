package zm.gov.moh.searchservice.model

import zm.gov.moh.searchservice.util.Sex
import java.time.LocalDate
import java.util.UUID

data class SubjectDTO(
        var id: UUID? = null,
        val firstName: String,
        val lastName: String,
        val sex: Sex,
        val dateOfBirth: LocalDate,
        var fingerprintData: MutableList<FingerprintDataDTO> = mutableListOf(),
        var auxiliaryIds: MutableList<AuxiliaryIdDTO> = mutableListOf(),
        var newClient: Boolean = false
) {
        var sourceSystemId = ""
}