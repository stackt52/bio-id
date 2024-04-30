package zm.gov.moh.identityservice.service

import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import zm.gov.moh.identityservice.util.Claim
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

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser().verifyWith(SECRET).build().parseSignedClaims(token).payload
        val authClaim = claims[Claim.SOURCE_SYS_ID.name]
        val authorities =
            if (authClaim == null)
                AuthorityUtils.NO_AUTHORITIES
            else
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                    authClaim.toString()
                )
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun createToken(claims: Map<String, Any>, userName: String): String {
        return Jwts.builder()
            .claims()
            .and()
            .claims(claims)
            .subject(userName)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 600)) // 10hrs
            .signWith(SECRET)
            .compact()
    }
}