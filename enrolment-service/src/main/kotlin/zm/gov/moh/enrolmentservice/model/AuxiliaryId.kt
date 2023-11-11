package zm.gov.moh.enrolmentservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class AuxiliaryId(
    @Id
    val auxiliaryId: Long,
    val type: String?,
    val value: String?
) {
    constructor() : this(0, "", "")
}