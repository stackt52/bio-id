package zm.gov.moh.searchservice.model

data class FingerprintImageDTO(
    val image: ByteArray,
    var sourceSystemId: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FingerprintImageDTO

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
