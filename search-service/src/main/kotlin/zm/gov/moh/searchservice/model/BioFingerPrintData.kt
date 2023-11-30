package zm.gov.moh.searchservice.model

import java.util.UUID

data class BioFingerPrintData(
        val id: UUID?,
        val pos: String?,
        val data: String?
)
