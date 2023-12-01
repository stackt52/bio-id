package zm.gov.moh.searchservice.model

import java.util.*

data class Subject(
    val id: UUID,
    val firstName: String,
    val surname: String,
    val sex: Char,
    val sourceSubjectId: String,
    val sourceSystemId: String
)
