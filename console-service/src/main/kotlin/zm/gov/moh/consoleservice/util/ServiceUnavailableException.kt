package zm.gov.moh.consoleservice.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
class ServiceUnavailableException(message:String):RuntimeException(message)