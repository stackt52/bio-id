package zm.gov.moh.enrolmentservice.model

import java.util.*

data class AuxiliaryIdDTO(
        val type: String,
        val value: String,
        val sourceSystemId: UUID
)