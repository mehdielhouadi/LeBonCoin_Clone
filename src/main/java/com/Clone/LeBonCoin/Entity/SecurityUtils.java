package com.Clone.LeBonCoin.Entity;

import com.Clone.LeBonCoin.Repository.VisitorRepo;
import com.Clone.LeBonCoin.Service.VisitorService;
import io.jsonwebtoken.*;
import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class SecurityUtils {
    private Encoder encoder;
    private VisitorRepo visitorRepo;
    private VisitorService visitorService;
    @Autowired
    public SecurityUtils(Encoder encoder, VisitorRepo visitorRepo, VisitorService visitorService){
        this.encoder = encoder;
        this.visitorRepo = visitorRepo;
        this.visitorService = visitorService;
    }
    String TOKEN_KEY = "secret_token";
    Long EXPIRATION_DATE = (long) 60*60*1000; // 1 hour

    public String encryptPassword(String password) throws EncoderException {
        return encoder.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public String createToken(Visitor v) {
        return Jwts.builder()
                .setSubject( v.getEmail() )
                .setIssuer("M2i")
                .setIssuedAt(new Date())
                .setExpiration(new Date( System.currentTimeMillis() +  EXPIRATION_DATE  ))
                .signWith( SignatureAlgorithm.HS512 , TOKEN_KEY)
                .compact();
    }

    public boolean isValidToken(String token) {
        String[] tokenSplited = token.split(" ");
        try {
            if (tokenSplited[0].equals("Bearer") ) {
                Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(tokenSplited[1]);
                return true;
            }

        } catch (ExpiredJwtException e) {
            System.out.println("JWT Expired");
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT Unsupported");
        } catch (MalformedJwtException e) {
            System.out.println("JWT is invalid");
        } catch (SignatureException e) {
            System.out.println("JWT Signature validation failed");
        } catch ( IllegalArgumentException e) {
            System.out.println("JWT is Null, empty, contain space");
        }
        return false;
    }

    public boolean visitorInDb(String token) {
        String email = getSubject(token);
        Visitor v = visitorRepo.findByEmail(email).orElse(null);
        if (v == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getSubject(String token) {
        return parseClaims(token.split(" ")[1]).getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(TOKEN_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isAdminToken(String token) {
        if(isValidToken(token)) {
            if (visitorService.isAdmin(visitorRepo.findByEmail(getSubject(token)).orElseThrow())) {
                return true;
            }
        }
        return false;
    }
}
