package zm.gov.moh.enrolmentservice.model

import java.time.LocalDate
import java.util.UUID

class Subject(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val sex: Char,
    val dateOfBirth: LocalDate,
    val sourceSystemCode: String,
    var fingerprintData: List<FingerprintData>,
    var auxiliaryIds: List<AuxiliaryId>
)
