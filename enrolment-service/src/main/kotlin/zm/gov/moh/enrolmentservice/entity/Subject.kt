package zm.gov.moh.enrolmentservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import zm.gov.moh.enrolmentservice.util.Constants
import zm.gov.moh.enrolmentservice.util.Sex
import java.time.LocalDate
import java.util.UUID

@Table(name = "subject", schema = "client")
data class Subject(
        @Id
        var id: UUID = UUID.randomUUID(),
        val firstName: String = "",
        val lastName: String = "",
        val sex: Sex = Sex.M,
        @Column("dob")
        val dateOfBirth: LocalDate = Constants.NULL_DATE,
) 