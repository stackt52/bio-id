package zm.gov.moh.enrolmentservice


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import zm.gov.moh.enrolmentservice.model.Subject
import zm.gov.moh.enrolmentservice.repository.AuxiliaryRepository
import zm.gov.moh.enrolmentservice.repository.SubjectRepository
import zm.gov.moh.enrolmentservice.service.EnrolmentService
import java.time.LocalDate
import java.util.*


@SpringBootTest
class EnrolmentServiceApplicationTests {

	private val subjectRepository = Mockito.mock(SubjectRepository::class.java)
	private val auxiliaryRepository = Mockito.mock(AuxiliaryRepository::class.java)

	@InjectMocks
	private var enrolmentService: EnrolmentService = EnrolmentService(subjectRepository, auxiliaryRepository)

	@BeforeEach
	fun init() {
		MockitoAnnotations.openMocks(this)
	}

	@Test
	fun contextLoads() {
	}

	@Test
	fun testAddSubject() {
		// Given
		val subject = Subject(UUID.randomUUID(),"Test","Test",'M', LocalDate.now(), mutableListOf(),mutableListOf())
		`when`(subjectRepository.save(any(Subject::class.java))).thenReturn(subject)

		// When
		val result = enrolmentService.addSubject(subject)

		// Then
		assertEquals(subject, result)
		verify(subjectRepository).save(subject)
	}

	@Test
	fun testFindAll() {
		// Given
		val subjects = listOf(Subject(UUID.randomUUID(),"Test","Test",'M', LocalDate.now(), mutableListOf(),mutableListOf()))
		`when`(subjectRepository.findAll()).thenReturn(subjects)

		// When
		val result = enrolmentService.findAll()

		// Then
		assertEquals(subjects.size, result.size)
		verify(subjectRepository).findAll()
	}

	@Test
	fun testFindById() {
		// Given
		val uuid = UUID.randomUUID()

		val subject = Subject(UUID.randomUUID(),"Test","Test",'M', LocalDate.now(), mutableListOf(),mutableListOf())

		`when`(subjectRepository.getReferenceById(uuid)).thenReturn(subject)

		// When
		val result = enrolmentService.findById(uuid)

		// Then
		assertEquals(subject, result)
		verify(subjectRepository).getReferenceById(uuid)
	}

	@Test
	fun testUpdateById() {
		// Given
		val subject = Subject(UUID.randomUUID(),"Test","Test",'M', LocalDate.now(), mutableListOf(),mutableListOf())
		`when`(subjectRepository.save(subject)).thenReturn(subject)

		// When
		val result = enrolmentService.updateById(subject)

		// Then
		assertEquals(subject, result)
		verify(subjectRepository).save(subject)
	}

	@Test
	fun testDeleteById() {
		// Given
		val uuid = UUID.randomUUID()

		// When
		enrolmentService.deleteById(uuid)

		// Then
		verify(subjectRepository).deleteById(uuid)
	}

}