package zm.gov.moh.searchservice.model

import com.machinezoo.sourceafis.FingerprintTemplate
import zm.gov.moh.searchservice.utils.Constants
import java.util.UUID

data class BioFingerPrintData(
    val subjectId: UUID? = Constants.UNASSIGNED_UUID,
    val pos: String? = "",
    val data: FingerprintTemplate? = null
)
