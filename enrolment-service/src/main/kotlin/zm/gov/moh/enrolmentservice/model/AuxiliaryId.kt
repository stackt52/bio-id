package zm.gov.moh.enrolmentservice.model

import java.util.UUID

data class AuxiliaryId(
    val subjectId: UUID,
    val type: String,
    val value: String
)