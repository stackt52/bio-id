package zm.gov.moh.enrolmentservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zm.gov.moh.enrolmentservice.model.Subject
import java.util.UUID
@Repository
interface SubjectRepository : JpaRepository<Subject, UUID> {
}