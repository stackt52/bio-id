package zm.gov.moh.enrolmentservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zm.gov.moh.enrolmentservice.model.AuxiliaryId
@Repository
interface AuxiliaryRepository : JpaRepository<AuxiliaryId, Long> {
}