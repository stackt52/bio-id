package zm.gov.moh.apigateway.filter

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import java.util.function.Predicate

@Service
class RouteValidator {
    companion object {
        private val openApiEndpoints = listOf(
            "/auth/users/sign-in",
            "/auth/users/token"
        )
    }

    var isSecured: Predicate<ServerHttpRequest> =
        Predicate { request: ServerHttpRequest ->
            openApiEndpoints
                .stream().noneMatch { uri: String ->
                    request.uri.path.contains(uri)
                }
        }
}