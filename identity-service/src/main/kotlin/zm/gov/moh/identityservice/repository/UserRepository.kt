package zm.gov.moh.identityservice.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import zm.gov.moh.identityservice.entity.User
import java.util.UUID

interface UserRepository : R2dbcRepository<User, UUID>