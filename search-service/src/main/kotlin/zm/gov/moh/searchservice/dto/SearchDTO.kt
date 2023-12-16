package zm.gov.moh.searchservice.dto

import zm.gov.moh.searchservice.model.AuxiliaryId
import zm.gov.moh.searchservice.utils.Constants
import java.time.LocalDate
import java.util.*

data class SearchDTO(
    var id: UUID = Constants.NULL_UUID,
    var firstName: String = "",
    var lastName: String = "",
    var sex: Char = Constants.NULL_CHAR,
    var dateOfBirth: LocalDate = LocalDate.now(),
    var auxiliaryIds: MutableList<AuxiliaryId>? = null
)
