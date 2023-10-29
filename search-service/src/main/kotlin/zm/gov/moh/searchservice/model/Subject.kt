package zm.gov.moh.searchservice.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
data class Subject(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID?,
        val firstName: String?,
        val lastName: String?,
        val sex: Char?,
        val dateOfBirth: LocalDate,
        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "subject")
        var bioFingerprints: MutableList<BioFingerPrintData>,
        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "subject")
        var auxiliaryIds: MutableList<AuxiliaryId>
){
    constructor() : this(UUID.randomUUID(), "", "",'M', LocalDate.now(), mutableListOf(),mutableListOf())
}