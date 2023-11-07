package zm.gov.moh.searchservice.extern

import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import zm.gov.moh.searchservice.model.BioFingerPrintData

@HttpExchange
interface GetBioFingerPrintData {
    @GetExchange("/biometric-data")
    fun getAll(): List<BioFingerPrintData>
}