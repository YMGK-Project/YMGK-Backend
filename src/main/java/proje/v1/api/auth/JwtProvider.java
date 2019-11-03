package proje.v1.api.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import proje.v1.api.message.RequestLogin;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("$(secret-key)")
    private String secretKey;
    private int expiration = 600000;

    String getJwtFromHeader(String authHeader){
        if(authHeader != null && authHeader.startsWith(SecurityConstants.TOKEN_PREFIX))
            return authHeader.replace(SecurityConstants.TOKEN_PREFIX,"");
        return null;
    }

    public String generateJsonWebToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    String getUsernameFrom(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    boolean isJsonWebToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    boolean isExpirationPassed(String token){
        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        return expiration.getTime() < new Date().getTime();
    }

}
