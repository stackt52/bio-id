package zm.gov.moh.apigateway.util

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class UnauthorizedRequestException(message: String) : RuntimeException(message)