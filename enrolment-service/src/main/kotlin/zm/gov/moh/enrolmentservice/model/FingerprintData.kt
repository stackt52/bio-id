package zm.gov.moh.enrolmentservice.model

import zm.gov.moh.enrolmentservice.util.Position

data class FingerprintData(
    val pos: Position,
    val data: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FingerprintData

        if (pos != other.pos) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}
