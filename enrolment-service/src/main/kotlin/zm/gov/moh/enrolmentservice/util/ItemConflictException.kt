package zm.gov.moh.enrolmentservice.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class ItemConflictException(message:String):RuntimeException(message)