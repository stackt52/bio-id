package zm.gov.moh.searchservice.model

data class SearchDTO(
    val fingerPrintTemplate: ByteArray,
    val sourceSystemId: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchDTO

        if (!fingerPrintTemplate.contentEquals(other.fingerPrintTemplate)) return false
        if (sourceSystemId != other.sourceSystemId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fingerPrintTemplate.contentHashCode()
        result = 31 * result + sourceSystemId.hashCode()
        return result
    }
}
