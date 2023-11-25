package zm.gov.moh.biodataservice.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable
import java.util.UUID

@Data
@NoArgsConstructor
@AllArgsConstructor
class FingerprintDao(val subjectId: UUID, val data: List<FingerprintData>) : Serializable {
    override fun toString(): String {
        return "{subjectId: ${subjectId}, data: ${data}}"
    }
}