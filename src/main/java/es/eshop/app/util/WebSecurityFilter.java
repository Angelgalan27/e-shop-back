package es.eshop.app.util;

import es.eshop.app.service.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class WebSecurityFilter extends OncePerRequestFilter {

    private final IUserService userService;

    private final JwtTokenUtil jwtTokenUtil;
    public WebSecurityFilter(IUserService userService,
                             JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String token = null;
        if(Objects.nonNull(authorizationHeader)) {
            token = removeString(authorizationHeader, "Bearer ");
            email = jwtTokenUtil.getEmailFromToken(token);
        } else {
            log.error("error authorization");
            // TODO: EXCEPTION
        }

        if(Objects.nonNull(email) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = userService.loadUserByUsername(email);
            if(Objects.nonNull(userDetails) && Boolean.TRUE.equals(jwtTokenUtil.validatedToken(token, userDetails))) {
                createContextSecurity(userDetails, request);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void createContextSecurity(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String removeString(String text, String prefix) {
        return StringUtils.startsWith(text, prefix) ? text.substring(prefix.length()) : text;
    }
}
