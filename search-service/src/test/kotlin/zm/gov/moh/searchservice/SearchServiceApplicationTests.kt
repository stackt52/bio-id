//package zm.gov.moh.searchservice
//
//import com.machinezoo.sourceafis.FingerprintTemplate
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.Mockito.`when`
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//import org.springframework.boot.test.context.SpringBootTest
//import reactor.core.publisher.Flux
//import zm.gov.moh.searchservice.client.BioDataClient
//import zm.gov.moh.searchservice.model.SearchDTO
////import zm.gov.moh.searchservice.repository.SearchRepository
//import java.nio.file.Files
//import java.nio.file.Path
//import java.util.*
//
//@SpringBootTest
//class SearchServiceApplicationTests {
//	private val bioDataClient = Mockito.mock(BioDataClient::class.java)
//	private val enrolmentClient = Mockito.mock(EnrolmentClient::class.java)
//	//private val searchRepository = Mockito.mock(SearchRepository::class.java)
//	private val searchPayload = Mockito.mock(SearchDTO::class.java)
//
////	@InjectMocks
////	private var searchService: SearchService = SearchService(searchRepository)
//
//	@BeforeEach
//	fun init() {
//		MockitoAnnotations.openMocks(this)
//	}
//
//	@Test
//	fun testFindClientDetails() {
////		val fingerPrintImage = Files.readAllBytes(Path.of("kotlin/zm/gov/moh/searchservice/assets/fingerprint.jpg"))
////
////		val probe = FingerprintTemplate(fingerPrintImage)
////
////		val uuid = UUID.randomUUID()
////
////		val subject = Subject(UUID.randomUUID(), "Jane", "Doe", 'F', "1", "1")
////		val bioFingerPrintData = listOf(FingerprintData(UUID.randomUUID(), "Test", probe))
////
////		`when`(getSubject.getByClientUuid(uuid)).thenReturn(subject)
////		`when`(getBioFingerPrintData.getAll()).thenReturn(bioFingerPrintData)
////
////		val result = searchService.findClientDetails(probe)
////
////		assertEquals(subject, result)
////
////		verify(getSubject).getByClientUuid(uuid)
////		verify(getBioFingerPrintData).getAll()
//	}
//
//	@Test
//	fun testFindSubject() {
//
//	}
//
//	@Test
//	fun testFindFingerprint() {
//		val fingerprintImage = Files.readAllBytes(Path.of("search-service/src/test/kotlin/zm/gov/moh/searchservice/assets/fingerprint.jpg"))
//
//		val probe = FingerprintTemplate(fingerprintImage).toByteArray()
//
//		val srcSystemId = UUID.randomUUID()
//
//		val fingerprintDao: List<FingerprintDao> = mutableListOf()
//
//		val fingerprint: Flux<FingerprintDao> = Flux.fromIterable(fingerprintDao)
//
//		`when`(bioDataClient.getAllBioData()).thenReturn(fingerprint)
//
//		val searchPayload = SearchDTO(probe, srcSystemId)
//
//		//val result = searchService.findFingerprint(searchPayload)
//
//	}
//
//}
