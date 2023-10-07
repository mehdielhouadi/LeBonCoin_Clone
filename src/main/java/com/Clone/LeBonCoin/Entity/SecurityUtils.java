package com.Clone.LeBonCoin.Entity;

import com.Clone.LeBonCoin.Repository.VisitorRepo;
import com.Clone.LeBonCoin.Service.VisitorService;
import io.jsonwebtoken.*;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class SecurityUtils {
    private VisitorRepo visitorRepo;
    private VisitorService visitorService;
    @Autowired
    public SecurityUtils(VisitorRepo visitorRepo, VisitorService visitorService){
        this.visitorRepo = visitorRepo;
        this.visitorService = visitorService;
    }
    String TOKEN_KEY = "secret_token";
    Long EXPIRATION_DATE = (long) 60*60*1000; // 1 hour
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    public Visitor getUserByCredentials(Credentials credential) {
        return visitorRepo.findByEmail(credential.getEmail()).orElse(null);
    }
    public String createToken(Visitor user) {
        return Jwts.builder()
                .setSubject( user.getEmail())
                .setIssuer("C'est moi")
                .setIssuedAt( new Date() )
                .setExpiration( new Date( System.currentTimeMillis() + EXPIRATION_DATE))
                .signWith( SignatureAlgorithm.HS512, TOKEN_KEY )
                .compact();
    }
    public boolean isValidToken(String bearerToken) {
        try {
            String token = getTokenFromBearerToken(bearerToken);
            Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public String getTokenFromBearerToken(String bearerToken) throws Exception {

        if ( bearerToken.startsWith("Bearer ") ) {
            return bearerToken.split(" ")[1];
        }
        throw new Exception("INVALID TOKEN");
    }
    public Visitor getUserFromToken(String bearerToken) {
        try {
            String token = getTokenFromBearerToken(bearerToken);
            String email = getSubject(token);
            Visitor user = visitorRepo.findByEmail(email).orElseThrow();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(TOKEN_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean canAccess(String bearerToken, String role) {
        String token;
        try {
            token = getTokenFromBearerToken(bearerToken);
            String email = getSubject(token);
            return visitorRepo.findRoleByEmailAndType(email, role).isPresent();
        } catch (Exception e) {
            return false;
        }
    }
}
