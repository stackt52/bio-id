package zm.gov.moh.consoleservice.model

import org.springframework.http.codec.multipart.FilePart
import zm.gov.moh.consoleservice.util.Sex
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
    val rightThumb: FilePart?,
    val rightIndex: FilePart?,
    val rightMiddle: FilePart?,
    val rightRing: FilePart?,
    val rightPinky: FilePart?,
    val leftThumb: FilePart?,
    val leftIndex: FilePart?,
    val leftMiddle: FilePart?,
    val leftRing: FilePart?,
    val leftPinky: FilePart?
)
