package zm.gov.moh.enrolmentservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.UUID
import kotlin.jvm.Transient

@Table(name = "subject", schema = "client")
data class Subject(
        @Id
        var id: UUID = UUID.randomUUID(),
        val firstName: String = "",
        val lastName: String = "",
        val sex: Char = 'M',
        val dateOfBirth: LocalDate = LocalDate.of(1900, 1, 1),
        val sourceSystemCode: String = "",
        @Transient
        var fingerprintData: MutableList<FingerprintData> = mutableListOf(),
        var auxiliaryIds: MutableList<AuxiliaryId> = mutableListOf()
) 