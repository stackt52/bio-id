package zm.gov.moh.enrolmentservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import zm.gov.moh.enrolmentservice.model.AuxiliaryId

interface AuxiliaryRepository : JpaRepository<AuxiliaryId, Long> {
}