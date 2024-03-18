package zm.gov.moh.enrolmentservice.model

import java.util.UUID

data class SearchDTO(
    val fingerprintTemplate: ByteArray,
    val sourceSystemId: UUID?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchDTO

        if (!fingerprintTemplate.contentEquals(other.fingerprintTemplate)) return false
        if (sourceSystemId != other.sourceSystemId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fingerprintTemplate.contentHashCode()
        result = 31 * result + (sourceSystemId?.hashCode() ?: 0)
        return result
    }
}