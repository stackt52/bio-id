package zm.gov.moh.searchservice.model

import java.time.LocalDate
import java.util.*

data class Subject(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val sex: Char,
    val dateOfBirth: LocalDate,
    val sourceSystemCode: String,
    val fingerprintData: MutableList<FingerprintData>,
    val auxiliaryIds: MutableList<AuxiliaryId>
)
