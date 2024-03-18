package zm.gov.moh.enrolmentservice.entity

import org.springframework.data.relational.core.mapping.Table

@Table(name = "auxiliary_id", schema = "client")
data class AuxiliaryId(
        var type: String = "",
        var value: String = ""
)