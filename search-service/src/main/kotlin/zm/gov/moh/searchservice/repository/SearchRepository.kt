package zm.gov.moh.searchservice.repository

import org.springframework.stereotype.Repository
import zm.gov.moh.searchservice.model.BioFingerPrintData
import java.util.UUID

@Repository
class SearchRepository {

    companion object {
        private val bioFingerPrintData = mutableListOf<BioFingerPrintData>()
    }

    fun getById(id: UUID): List<BioFingerPrintData> {
        return bioFingerPrintData.filter { it.subject?.id == id }
    }

    fun updateBioFingerPrintData(data: List<BioFingerPrintData>) {

    }
}