package br.com.insidesoftwares.commons.utils;

import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String BEARER = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.valid}")
    private long valid;

    private HMACSigner hmacSigner;

    @PostConstruct
    private void inicializa(){
        this.hmacSigner = HMACSigner.newSHA512Signer(secret);
    }

    public String generateToken(String login,  String email) {
        return doGenerateToken(login, email);
    }

    private String doGenerateToken(String login,  String email) {
        JWT jwt = new JWT().setIssuer("ISSecurity")
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(login)
                .setUniqueId(email)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(valid));
        return JWT.getEncoder().encode(jwt, hmacSigner);
    }

    public long timeExpiration(){
        return valid*60;
    }

}
