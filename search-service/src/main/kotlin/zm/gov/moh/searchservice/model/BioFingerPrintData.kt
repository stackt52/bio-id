package zm.gov.moh.searchservice.model

import jakarta.persistence.*
import java.util.UUID

@Entity
data class BioFingerPrintData(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @ManyToOne
        @JoinColumn(name = "subject_id", referencedColumnName = "id")
        var subject: Subject?,
        val pos: String?,
        val data: String?
) {
    constructor() : this(0,Subject(),"","")
}
