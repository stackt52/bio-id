package zm.gov.moh.identityservice.dto

import java.util.UUID

data class UserDTO(
    val id : UUID,
    val name: String,
    val email: String,
    var password: String
)
