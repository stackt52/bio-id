package zm.gov.moh.identityservice.dto

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class UserDTO(
    var id: UUID?,
    val name: String,
    val email: String,
    var password: String,
    val active: Boolean = true,
    val lastDateUpdated: LocalDate = LocalDate.now(),
    val lastTimeUpdate: LocalTime = LocalTime.now()
)
