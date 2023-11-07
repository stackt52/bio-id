package zm.gov.moh.searchservice.utils

import java.util.*

object Constants {
    private const val UNASSIGNED_UUID_STRING = "00000000-0000-0000-0000-000000000000"

    val UNASSIGNED_UUID: UUID = UUID.fromString(UNASSIGNED_UUID_STRING)

    // according to sourceAFIS docs 40 corresponds to false match rate 0.01% which is good starting point
    const val FINGERPRINT_SIMILARITY_THRESHOLD = 40
}