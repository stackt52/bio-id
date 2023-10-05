package zm.gov.moh.enrolmentservice.model

import java.util.UUID

data class BioFingerPrintData(
    val subjectId: UUID,
    val pos: String,
    val data: String
)
