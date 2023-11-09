package zm.gov.moh.enrolmentservice.model

import jakarta.persistence.*

@Entity
data class AuxiliaryId(
        @Id
        val auxiliaryId: Long,
        val type: String?,
        val value: String?
) {
    constructor() : this(0, "", "")
}