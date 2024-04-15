package zm.gov.moh.identityservice.dto

import java.util.UUID

data class UserDTO(
    var id : UUID?,
    val name: String,
    val email: String,
    val password: String
)
