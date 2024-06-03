package zm.gov.moh.enrolmentservice.model

import org.springframework.http.codec.multipart.FilePart
import zm.gov.moh.enrolmentservice.util.Sex
import java.time.LocalDate

data class EnrolmentDTO(
    // client details
    val firstName: String,
    var middleName: String? = null,
    val lastName: String,
    val sex: Sex,
    val dateOfBirth: LocalDate,
    // client ID in source system
    val clientIdName: String,
    val clientIdValue: String,
    // client fingerprint images
    val rightThumb: FilePart? = null,
    val rightIndex: FilePart? = null,
    val rightMiddle: FilePart? = null,
    val rightRing: FilePart? = null,
    val rightPinky: FilePart? = null,
    val leftThumb: FilePart? = null,
    val leftIndex: FilePart? = null,
    val leftMiddle: FilePart? = null,
    val leftRing: FilePart? = null,
    val leftPinky: FilePart? = null
)
