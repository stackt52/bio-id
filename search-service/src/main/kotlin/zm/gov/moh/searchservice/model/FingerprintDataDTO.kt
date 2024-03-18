package zm.gov.moh.searchservice.model

import zm.gov.moh.searchservice.util.Position

data class FingerprintDataDTO(
    val position: Position,
    val fingerPrintTemplate: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FingerprintDataDTO

        if (position != other.position) return false
        if (!fingerPrintTemplate.contentEquals(other.fingerPrintTemplate)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + fingerPrintTemplate.contentHashCode()
        return result
    }
}
