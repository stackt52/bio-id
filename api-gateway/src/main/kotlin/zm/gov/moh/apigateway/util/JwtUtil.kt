package zm.gov.moh.apigateway.util

import io.jsonwebtoken.Jwts

// We're adding jwt token validation logic in the API Gateway
// service so that we reduce on network calls to the identity service
// for verifying tokens.
class JwtUtil {
    companion object {
        private val SECRET = Jwts.SIG.HS256.key().build()
        fun validateToken(token: String) {
            Jwts.parser().verifyWith(SECRET).build().parseSignedClaims(token)
        }
    }
}