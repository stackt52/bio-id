package zm.gov.moh.searchservice.model

import java.time.LocalDate
import java.util.UUID

class Subject(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val sex: Char,
    val dateOfBirth: LocalDate,
    val bioFingerprints: List<BioFingerPrintData>
)
