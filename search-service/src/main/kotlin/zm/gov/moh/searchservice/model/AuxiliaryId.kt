package zm.gov.moh.searchservice.model

import java.util.UUID

data class AuxiliaryId(
        val auxiliaryId: Long,
        var subject_id: UUID?,
        val type: String?,
        val value: String?
)