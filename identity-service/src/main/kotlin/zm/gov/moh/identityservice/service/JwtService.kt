package zm.gov.moh.identityservice.service

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*


@Service
class JwtService {
    companion object {
        private val SECRET = Jwts.SIG.HS256.key().build()
    }

    fun validateToken(token: String) {
        Jwts.parser().verifyWith(SECRET).build().parseSignedClaims(token)
    }

    fun generateToken(userName: String, claims: Map<String, Any>): String {
        return createToken(claims, userName)
    }

    fun createToken(claims: Map<String, Any>, userName: String): String {
        return Jwts.builder()
            .claims()
            .and()
            .claims(claims)
            .subject(userName)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 30))
            .signWith(SECRET)
            .compact()
    }

}