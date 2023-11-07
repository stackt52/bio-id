package zm.gov.moh.searchservice.model

import com.machinezoo.sourceafis.FingerprintTemplate

data class SearchPayload(
    val fingerPrintTemplate: FingerprintTemplate? = null
)
