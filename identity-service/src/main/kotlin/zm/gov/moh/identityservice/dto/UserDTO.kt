package zm.gov.moh.identityservice.dto

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class UserDTO(
    var id: UUID?,
    val name: String,
    val email: String,
    val password: String,
    val active: Boolean,
    val lastDateUpdated: LocalDate,
    val lastTimeUpdate: LocalTime
)