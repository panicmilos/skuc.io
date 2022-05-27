package skuc.io.skucioapp.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import skuc.io.skuciocore.security.utils.JwtUtil;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String headerString;

    @Value("${jwt.token.prefix}")
    public String tokenPrefix;

    private final UserDetailsService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(UserDetailsService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader(headerString);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(tokenPrefix)) {
            jwt = authorizationHeader.replace(tokenPrefix, "");
            try {
                username = jwtUtil.extractUsernameFromToken(jwt);
            } catch (IllegalArgumentException | SignatureException | ExpiredJwtException | MalformedJwtException ignored) {
                System.out.println(ignored.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                userDetails = userService.loadUserByUsername(username);

                if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt, userDetails))) {
                    var usernamePasswordAuthenticationToken =
                            jwtUtil.getAuthenticationToken(jwt, userDetails);
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (UsernameNotFoundException ignored) {
                System.out.println(ignored.getMessage());
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
