package zm.gov.moh.enrolmentservice.model

import jakarta.persistence.*

@Entity
data class AuxiliaryId(
        @Id
        val auxiliaryId: Long,
        @ManyToOne
        @JoinColumn(name = "subject_id", referencedColumnName = "id")
        var subject: Subject?,
        val type: String?,
        val value: String?
) {
    constructor() : this(0, Subject(), "", "")
}