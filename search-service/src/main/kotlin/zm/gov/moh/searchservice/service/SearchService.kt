package zm.gov.moh.searchservice.service

import com.machinezoo.sourceafis.FingerprintMatcher
import com.machinezoo.sourceafis.FingerprintTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zm.gov.moh.searchservice.extern.GetBioFingerPrintData
import zm.gov.moh.searchservice.extern.GetClientDetails
import zm.gov.moh.searchservice.model.BioFingerPrintData
import zm.gov.moh.searchservice.model.SearchPayload
import zm.gov.moh.searchservice.model.Subject
import zm.gov.moh.searchservice.utils.Constants

@Service
class SearchService {
    fun identifyFingerprint(probe: FingerprintTemplate, candidates: List<BioFingerPrintData>): BioFingerPrintData? {
        val matcher = FingerprintMatcher(probe)

        var match = BioFingerPrintData()

        var max = Double.NEGATIVE_INFINITY

        for (candidate in candidates) {
           val similarity = matcher.match(candidate.data)

            if (similarity > max) {
                max = similarity
                match = candidate
            }
        }

        if (max >= Constants.FINGERPRINT_SIMILARITY_THRESHOLD) return match

        return null
    }
}