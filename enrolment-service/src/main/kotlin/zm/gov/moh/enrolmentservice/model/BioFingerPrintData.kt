package zm.gov.moh.enrolmentservice.model

import jakarta.persistence.*

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
