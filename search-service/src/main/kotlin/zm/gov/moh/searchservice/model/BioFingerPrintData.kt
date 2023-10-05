package zm.gov.moh.searchservice.model

import java.util.UUID

data class BioFingerPrintData(val subjectId: UUID, val pos: String, val data: String)
