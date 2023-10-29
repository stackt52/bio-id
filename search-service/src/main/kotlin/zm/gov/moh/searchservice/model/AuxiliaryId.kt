package zm.gov.moh.searchservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID


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