package zm.gov.moh.enrolmentservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import zm.gov.moh.enrolmentservice.model.BioFingerPrintData

interface FingerPrintDataRepository : JpaRepository<BioFingerPrintData, Long> {
}