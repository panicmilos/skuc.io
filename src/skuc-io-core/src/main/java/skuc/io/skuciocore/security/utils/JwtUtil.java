package skuc.io.skuciocore.security.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import com.google.common.base.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import skuc.io.skuciocore.exceptions.AuthorizationException;

@Component
public class JwtUtil {

    @Value("${jwt.token.validity}")
    public long tokenValidity;

    @Value("${jwt.signing.key}")
    public String signingKey;

    @Value("${jwt.authorities.key}")
    public String authoritiesKey;

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRoleFromToken(String token) {
        var claims = getAllClaimsFromToken(token);
        var role = claims.get(authoritiesKey).toString();

        return role.replace("ROLE_", "");
    }

    public String extractRoleFromSecurityContextHolder() {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(authorities.isEmpty()) {
            throw new AuthorizationException();
        }

        var role = authorities.iterator().next().toString();
        return role.replace("ROLE_", "");
    }

    public Date extractExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        var expiration = extractExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication, String userId, String groupId) {
        var authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("UserId", userId)
                .claim("GroupId", groupId)
                .claim(authoritiesKey, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final UserDetails userDetails) {

        var jwtParser = Jwts.parser().setSigningKey(signingKey);

        var claimsJws = jwtParser.parseClaimsJws(token);

        var claims = claimsJws.getBody();

        var authorities =
                Arrays.stream(claims.get(authoritiesKey).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
