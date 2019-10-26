package proje.v1.api.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import proje.v1.api.message.RequestLogin;

import java.util.Date;

public class JwtProvider {

    @Value("$(secret-key)")
    private String secretKey;
    private int expiration = 120000;

    public String generateJwt(RequestLogin requestLogin){
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expiration))
                .setSubject(requestLogin.getUsername()+" "+requestLogin.getPassword())
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact();
    }

}
