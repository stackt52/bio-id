package zm.gov.moh.enrolmentservice.util

import java.time.LocalDate
import java.util.*

object Constants {
    private const val UNASSIGNED_UUID_STRING = "00000000-0000-0000-0000-000000000000"

    val NULL_UUID: UUID = UUID.fromString(UNASSIGNED_UUID_STRING)

    const val NULL_CHAR: Char = '\u0000'

    val NULL_DATE: LocalDate = LocalDate.of(1990, 1, 1)
}