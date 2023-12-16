package zm.gov.moh.enrolmentservice.dto

import zm.gov.moh.enrolmentservice.model.AuxiliaryId
import java.time.LocalDate
import java.util.*

data class SearchDTO(
    var id: UUID = UUID.randomUUID(),
    var firstName: String = "",
    var lastName: String = "",
    var sex: Char = 'M',
    var dateOfBirth: LocalDate = LocalDate.now(),
    var auxiliaryIds: MutableList<AuxiliaryId>  = mutableListOf()
)
