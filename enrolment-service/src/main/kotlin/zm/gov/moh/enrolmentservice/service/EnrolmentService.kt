package zm.gov.moh.enrolmentservice.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zm.gov.moh.enrolmentservice.model.Subject
import zm.gov.moh.enrolmentservice.repository.AuxiliaryRepository
import zm.gov.moh.enrolmentservice.repository.FingerPrintDataRepository
import zm.gov.moh.enrolmentservice.repository.SubjectRepository
import java.util.*

@Service
class EnrolmentService(
        @Autowired
        private val subjectRepository: SubjectRepository,
        @Autowired
        private val auxiliaryRepository: AuxiliaryRepository,
        @Autowired
        private val fingerPrintDataRepository: FingerPrintDataRepository,
) {

    @Transactional
    fun addSubject(subject: Subject): Subject {
        return subjectRepository.save(subject)
    }

    fun findAll(): List<Subject> {
        return subjectRepository.findAll()
    }
    fun findById(id: UUID): Subject {
        return  subjectRepository.getReferenceById(id)
    }

}