package zm.gov.moh.searchservice.model

import java.util.UUID

data class MatchScore(val subjectId: UUID, val score: Double)