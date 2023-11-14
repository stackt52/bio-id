package zm.gov.moh.searchservice

import com.machinezoo.sourceafis.FingerprintTemplate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import zm.gov.moh.searchservice.extern.GetBioFingerPrintData
import zm.gov.moh.searchservice.extern.GetClientDetails
import zm.gov.moh.searchservice.model.BioFingerPrintData
import zm.gov.moh.searchservice.model.Subject
import zm.gov.moh.searchservice.service.SearchService
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

@SpringBootTest
class SearchServiceApplicationTests {
	private val getBioFingerPrintData = Mockito.mock(GetBioFingerPrintData::class.java)
	private val getClientDetails = Mockito.mock(GetClientDetails::class.java)

	@InjectMocks
	private var searchService: SearchService = SearchService(getBioFingerPrintData, getClientDetails)

	@BeforeEach
	fun init() {
		MockitoAnnotations.openMocks(this)
	}

	@Test
	fun testFindClientDetails() {
		val fingerPrintImage = Files.readAllBytes(Path.of("kotlin/zm/gov/moh/searchservice/assets/fingerprint.jpg"))

		val probe = FingerprintTemplate(fingerPrintImage)

		val uuid = UUID.randomUUID()

		val subject = Subject(UUID.randomUUID(), "Jane", "Doe", 'F', LocalDate.now(), mutableListOf())
		val bioFingerPrintData = listOf(BioFingerPrintData(UUID.randomUUID(), "Test", probe))

		`when`(getClientDetails.getByClientUuid(uuid)).thenReturn(subject)
		`when`(getBioFingerPrintData.getAll()).thenReturn(bioFingerPrintData)

		val result = searchService.findClientDetails(probe)

		assertEquals(subject, result)

		verify(getClientDetails).getByClientUuid(uuid)
		verify(getBioFingerPrintData).getAll()
	}

}
