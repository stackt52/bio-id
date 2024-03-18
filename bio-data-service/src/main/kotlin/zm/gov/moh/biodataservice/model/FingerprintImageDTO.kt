package zm.gov.moh.biodataservice.model

import zm.gov.moh.biodataservice.util.Constants
import zm.gov.moh.biodataservice.util.Position
import java.util.UUID

data class FingerprintImageDTO(
    val position: Position,
    val image: ByteArray,
) {
    var clientId: UUID = Constants.NULL_UUID
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FingerprintImageDTO

        if (position != other.position) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}