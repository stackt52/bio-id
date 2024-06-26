package zm.gov.moh.enrolmentservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table(name = "auxiliary_id", schema = "client")
data class AuxiliaryId(
        @Id
        var id: Int = 0,
        var subjectId: UUID,
        var type: String,
        var value: String,
        var sourceSystemId: UUID
)