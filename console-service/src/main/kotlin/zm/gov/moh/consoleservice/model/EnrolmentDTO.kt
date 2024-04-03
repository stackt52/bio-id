package zm.gov.moh.consoleservice.model

import org.springframework.web.multipart.MultipartFile
import zm.gov.moh.consoleservice.util.Sex
import java.time.LocalDate

data class EnrolmentDTO(
    // client details
    val firstName: String,
    val lastName: String,
    val sex: Sex,
    val dateOfBirth: LocalDate,
    // client ID in source system
    val clientIdName: String,
    val clientIdValue: String,
    // client fingerprint images
    val rightThumb: MultipartFile?,
    val rightIndex: MultipartFile?,
    val rightMiddle: MultipartFile?,
    val rightRing: MultipartFile?,
    val rightPinky: MultipartFile?,
    val leftThumb: MultipartFile?,
    val leftIndex: MultipartFile?,
    val leftMiddle: MultipartFile?,
    val leftRing: MultipartFile?,
    val leftPinky: MultipartFile?
)
