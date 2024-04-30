package zm.gov.moh.identityservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalTime
import java.util.*


@Table(name = "user", schema = "auth")
data class User(
    @Id
    var id: UUID = UUID.randomUUID(),
    val name: String,
    var email: String,
    var password: String,
    var active: Boolean,
    var lastDateUpdated: LocalDate = LocalDate.now(),
    var lastTimeUpdated: LocalTime = LocalTime.now()
)
