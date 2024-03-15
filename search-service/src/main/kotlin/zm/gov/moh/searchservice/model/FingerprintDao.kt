package zm.gov.moh.searchservice.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable
import java.util.UUID

@Data
@NoArgsConstructor
@AllArgsConstructor
class FingerprintDao(
    var subjectId: UUID = UUID.randomUUID(),
    var data: List<FingerprintData> = mutableListOf(),
    var srcSystemId: String = ""
) : Serializable {
    //
    override fun toString(): String {
        return "{subjectId: ${subjectId}, data: ${data}, srcSystemId: ${srcSystemId}}"
    }
}