package zm.gov.moh.enrolmentservice.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import zm.gov.moh.enrolmentservice.model.Subject
import java.util.UUID
interface SubjectRepository : R2dbcRepository<Subject, UUID>