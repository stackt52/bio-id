package zm.gov.moh.searchservice.utils

import com.machinezoo.sourceafis.FingerprintImage
import com.machinezoo.sourceafis.FingerprintMatcher
import com.machinezoo.sourceafis.FingerprintTemplate

object Fingerprint {

    // according to sourceAFIS docs 40 corresponds to false match rate 0.01% which is good starting point
    private const val FINGERPRINT_SIMILARITY_THRESHOLD = 40

    /**
     * Compare two fingerprint images and return a boolean indicating the similarity.
     *
     * @param probeData Byte array representing the fingerprint image data to probe.
     * @param candidateData Byte array representing the fingerprint image data to compare against.
     * @return True if the fingerprints are similar, false otherwise.
     */
    fun compareFingerprints(probeData: ByteArray, candidateData: ByteArray): Boolean {
        val probe = toFingerprintTemplate(probeData)

        val candidate = toFingerprintTemplate(candidateData)

        val matcher = FingerprintMatcher(probe)

        val similarity = matcher.match(candidate)

        return similarity >= FINGERPRINT_SIMILARITY_THRESHOLD
    }

    private fun toFingerprintTemplate(data: ByteArray): FingerprintTemplate {
        val image = FingerprintImage(data)

        return FingerprintTemplate(image)
    }
}