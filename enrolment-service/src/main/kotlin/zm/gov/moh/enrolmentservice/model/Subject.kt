package zm.gov.moh.enrolmentservice.model

import java.time.LocalDate
import java.util.UUID

class Subject(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val sex: Char,
    val dateOfBirth: LocalDate,
    var bioFingerprints: List<BioFingerPrintData>,
    var auxiliaryIds: List<AuxiliaryId>
)
