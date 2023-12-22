package zm.gov.moh.enrolmentservice.model

data class SearchPayload(
        val image: ByteArray,
        val sourceSystemId: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchPayload

        if (!image.contentEquals(other.image)) return false
        if (sourceSystemId != other.sourceSystemId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = image.contentHashCode()
        result = 31 * result + sourceSystemId.hashCode()
        return result
    }
}
