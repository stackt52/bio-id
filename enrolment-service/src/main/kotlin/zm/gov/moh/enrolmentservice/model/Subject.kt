package zm.gov.moh.enrolmentservice.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID
import kotlin.jvm.Transient

@Entity
data class Subject(
    @Id
    var id: UUID?,
    val firstName: String?,
    val lastName: String?,
    val sex: Char?,
    val dateOfBirth: LocalDate?,
    val sourceSystemCode: String,
    @Transient
    var bioFingerprints: MutableList<BioFingerPrintData>?,
    @OneToMany(targetEntity = AuxiliaryId::class, cascade = [CascadeType.ALL])
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    var auxiliaryIds: MutableList<AuxiliaryId>?
){
    constructor() : this(UUID.randomUUID(), "", "",'M', LocalDate.now(), "", mutableListOf(),mutableListOf())
}
