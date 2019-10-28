package proje.v1.api.auth;

import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import proje.v1.api.domian.user.UserRole;
import proje.v1.api.domian.user.Users;
import proje.v1.api.domian.user.UsersRepository;
import proje.v1.api.message.RequestLogin;
import proje.v1.api.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
            Optional<Users> user = userService.findById(username);
            user.ifPresent(users -> ContextHolder.user = users);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
