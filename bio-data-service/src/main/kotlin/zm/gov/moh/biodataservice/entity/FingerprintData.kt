package zm.gov.moh.biodataservice.entity

import zm.gov.moh.biodataservice.util.Position
import java.io.Serializable

//
data class FingerprintData(
    var pos: Position = Position.RIGHT_INDEX,
    var image: ByteArray = ByteArray(0)
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FingerprintData

        if (pos != other.pos) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "{pos: ${pos}, image: ${image}}"
    }

}
