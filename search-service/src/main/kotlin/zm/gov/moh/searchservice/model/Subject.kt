package zm.gov.moh.searchservice.model

import java.time.LocalDate
import java.util.UUID

data class Subject(
        var id: UUID?,
        val firstName: String?,
        val lastName: String?,
        val sex: Char?,
        val dateOfBirth: LocalDate,
        var bioFingerprints: MutableList<BioFingerPrintData>,
        var auxiliaryIds: MutableList<AuxiliaryId>
)