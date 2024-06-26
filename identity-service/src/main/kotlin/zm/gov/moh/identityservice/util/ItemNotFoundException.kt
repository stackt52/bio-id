package zm.gov.moh.identityservice.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ItemNotFoundException(message: String): RuntimeException(message)