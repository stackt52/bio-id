package zm.gov.moh.enrolmentservice.model

import java.time.LocalDate
import java.util.UUID

class Subject(
    var id: UUID = UUID.randomUUID(),
    var firstName: String = "",
    var lastName: String = "",
    var sex: Char = 'M',
    var dateOfBirth: LocalDate = LocalDate.now(),
    var sourceSystemCode: String = "",
    var fingerprintData: List<FingerprintData> = mutableListOf(),
    var auxiliaryIds: List<AuxiliaryId> = mutableListOf()
)
