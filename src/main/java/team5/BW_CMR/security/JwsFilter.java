package team5.BW_CMR.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.exceptions.UnauthorizedException;
import team5.BW_CMR.services.UtenteService;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwsFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private UtenteService utenteService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Token non presente");
        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
        UUID utenteId = jwtTools.extractIdFromToken(accessToken);
        Utente found = utenteService.getUtenteById(utenteId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(found, null, found.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws  ServletException {
        //return new AntPathMatcher().match("/auth/**", request.getServletPath());
        return new AntPathMatcher().match("/**", request.getServletPath());
    }
}
