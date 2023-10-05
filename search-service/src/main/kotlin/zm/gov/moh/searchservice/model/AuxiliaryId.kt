package zm.gov.moh.searchservice.model

import java.util.UUID

data class AuxiliaryId(val subjectId: UUID, val type: String, val value: String)