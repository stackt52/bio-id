package zm.gov.moh.consoleservice.util

import org.springframework.web.reactive.function.client.WebClientRequestException

class ServiceUtil {
    companion object {
        fun handleRequestResponseException(throwable: Throwable): Throwable {
            return if (throwable is WebClientRequestException)
                ServiceUnavailableException("Service unavailable")
            else
                UnauthorizedException("Invalid username or password")
        }
    }
}