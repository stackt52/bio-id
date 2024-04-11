package zm.gov.moh.identityservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*


@Table(name = "user", schema = "auth")
data class User(
    @Id
    var id: UUID = UUID.randomUUID(),
    val name: String,
    val email: String,
    var password: String
)
