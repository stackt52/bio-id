package zm.gov.moh.searchservice.model

data class SearchPayload(
    val image: ByteArray,
    val sourceSystemCode: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchPayload

        if (!image.contentEquals(other.image)) return false
        if (sourceSystemCode != other.sourceSystemCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = image.contentHashCode()
        result = 31 * result + sourceSystemCode.hashCode()
        return result
    }
}
