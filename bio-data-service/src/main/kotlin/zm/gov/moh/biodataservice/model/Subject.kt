package zm.gov.moh.biodataservice.model

import java.util.UUID

data class Subject(
    val id: UUID,
    val firstName: String,
    val surname: String,
    val sex: Char,
    val sourceSubjectId: String,
    val sourceSystemId: String
)
