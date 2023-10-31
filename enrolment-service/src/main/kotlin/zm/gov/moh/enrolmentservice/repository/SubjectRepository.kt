package zm.gov.moh.enrolmentservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import zm.gov.moh.enrolmentservice.model.Subject
import java.util.UUID

interface SubjectRepository : JpaRepository<Subject, UUID> {
}