package zm.gov.moh.enrolmentservice.model

import java.util.UUID

data class BioFingerPrintData(
    val id: Long?,
    var subject_id: UUID?,
    val pos: String?,
    val data: String?
)
