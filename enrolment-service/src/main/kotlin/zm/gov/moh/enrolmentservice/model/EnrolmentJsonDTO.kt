package zm.gov.moh.enrolmentservice.model

import zm.gov.moh.enrolmentservice.util.Sex
import java.time.LocalDate

data class EnrolmentJsonDTO(
    // client details
    var firstName: String,
    var middleName: String? = null,
    var lastName: String,
    var sex: Sex,
    var dateOfBirth: LocalDate,
    // client ID in source system
    var clientIdName: String,
    var clientIdValue: String,
    // client fingerprint images
    var fingerprints: List<FingerprintImageDTO> = mutableListOf()
)
