package zm.gov.moh.biodataservice.model

import zm.gov.moh.biodataservice.util.Position

data class FingerprintDataDTO(
    val position: Position,
    val serializedFingerprintTemplate: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FingerprintDataDTO

        if (position != other.position) return false
        if (!serializedFingerprintTemplate.contentEquals(other.serializedFingerprintTemplate)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + serializedFingerprintTemplate.contentHashCode()
        return result
    }
}
