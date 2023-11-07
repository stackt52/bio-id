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
class SearchService(
    @Autowired
    private val getBioFingerPrintData: GetBioFingerPrintData,
    @Autowired
    private val getClientDetails: GetClientDetails
) {
    fun findClientDetails(probe: FingerprintTemplate): Subject? {
        try {
            val candidates = getBioFingerPrintData.getAll()

            val clientBioFingerPrintData = identifyFingerprint(probe, candidates)

            if (clientBioFingerPrintData != null) {
                val clientUuid = clientBioFingerPrintData.subjectId!!

                return getClientDetails.getByClientUuid(clientUuid)
            }

            return null
        } catch (e: Exception) {
            throw Error("find client details error occurred: ", e)
        }
    }

    private fun identifyFingerprint(probe: FingerprintTemplate, candidates: List<BioFingerPrintData>): BioFingerPrintData? {
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