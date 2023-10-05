package zm.gov.moh.enrolmentservice.repository

import org.springframework.stereotype.Repository
import zm.gov.moh.enrolmentservice.model.Subject
import java.util.UUID

@Repository
class EnrolmentRepository {
    companion object {
        private val subjects = mutableListOf<Subject>()
    }

    fun addEnrolment(subject: Subject): Subject {
        subjects.add(subject)
        return subject
    }

    fun deleteEnrolment(id: UUID) {
        subjects.removeIf { it.id == id }
    }

    fun findById(id: UUID): Subject {
        return subjects.find { it.id == id }!!
    }

    fun findAll(): List<Subject> {
        return subjects
    }
}