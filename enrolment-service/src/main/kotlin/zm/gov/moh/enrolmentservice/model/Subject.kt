package zm.gov.moh.enrolmentservice.model

import java.time.LocalDate

class Subject(val id: Int, val firstName: String, val lastName: String, val sex: Char, val dateOfBirth: LocalDate, val bioFingerprints: List<BioFingerPrintData>)
