package zm.gov.moh.searchservice.model

import com.machinezoo.sourceafis.FingerprintTemplate
import zm.gov.moh.searchservice.util.Position
import java.util.UUID


data class SubjectFingerPrintDTO(
    val subjectId: UUID,
    val fingerprintTemplate: FingerprintTemplate,
    val position: Position,
    val srcSystemId: String = ""
)