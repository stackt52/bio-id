package zm.gov.moh.enrolmentservice.entity

import org.springframework.data.relational.core.mapping.Table

@Table(name = "system", schema = "client")
data class System(
        var id: String = "",
        var name: String = ""
)