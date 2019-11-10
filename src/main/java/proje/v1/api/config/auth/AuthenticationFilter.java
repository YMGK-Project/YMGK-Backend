package proje.v1.api.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import proje.v1.api.domian.user.Users;
import proje.v1.api.service.user.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ContextHolder.user = null;
        String jwt = jwtProvider.getJwtFromHeader(httpServletRequest.getHeader(SecurityConstants.HEADER_STRING));
        if(jwt != null && jwtProvider.isJsonWebToken(jwt) && !jwtProvider.isExpirationPassed(jwt)){
            String username = jwtProvider.getUsernameFrom(jwt);
            Users user = userService.findById(username);
            ContextHolder.user = user;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
