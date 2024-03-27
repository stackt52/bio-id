package zm.gov.moh.searchservice.model

import zm.gov.moh.searchservice.util.Sex
import java.time.LocalDate
import java.util.*

data class ClientDTO(
    var id: UUID? = null,
    val firstName: String,
    val lastName: String,
    val sex: Sex,
    val dateOfBirth: LocalDate,
    @Transient
    var fingerprintImages: MutableList<FingerprintImageDTO> = mutableListOf(),
    var auxiliaryIds: MutableList<AuxiliaryIdDTO> = mutableListOf(),
    var newClient: Boolean = false
)
