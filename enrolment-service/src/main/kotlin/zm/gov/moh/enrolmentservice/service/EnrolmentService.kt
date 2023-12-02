package zm.gov.moh.enrolmentservice.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zm.gov.moh.enrolmentservice.model.Subject
import zm.gov.moh.enrolmentservice.repository.AuxiliaryRepository
import zm.gov.moh.enrolmentservice.repository.SubjectRepository
import java.util.*

@Service
class EnrolmentService(
        @Autowired
        private val subjectRepository: SubjectRepository,
        @Autowired
        private val auxiliaryRepository: AuxiliaryRepository
) {
    @Transactional
    fun addSubject(subject: Subject): Subject {
        subject.id = UUID.randomUUID()
        return subjectRepository.save(subject)
    }

    fun findAll(): List<Subject> {
        return subjectRepository.findAll()
    }

    fun findById(id: UUID): Subject {
        return subjectRepository.getReferenceById(id)
    }

    fun updateById(subject: Subject): Subject {
        return subjectRepository.save(subject)
    }

    fun deleteById(id: UUID) {
        return subjectRepository.deleteById(id)
    }
}