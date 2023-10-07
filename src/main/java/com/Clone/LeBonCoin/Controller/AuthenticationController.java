package com.Clone.LeBonCoin.Controller;

import com.Clone.LeBonCoin.Entity.*;
import com.Clone.LeBonCoin.Repository.VisitorRepo;
import com.Clone.LeBonCoin.Service.VisitorService;
import io.jsonwebtoken.Jwts;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    SecurityUtils securityUtils;
    VisitorRepo visitorRepo;
    VisitorService visitorService;
    @Autowired
    public AuthenticationController(SecurityUtils securityUtils, VisitorRepo visitorRepo, VisitorService visitorService){
        this.securityUtils = securityUtils;
        this.visitorRepo = visitorRepo;
        this.visitorService = visitorService;
    }

    @PostMapping("/signup")
    public void CreateAccount(@RequestBody Visitor v) {
        String encodedPassword = securityUtils.encryptPassword(v.getPassword());
        v.setPassword( encodedPassword );
        visitorRepo.save(v);
    }

    @PostMapping("/login")
    public String login(@RequestBody Credentials credentials) {
        Visitor v = visitorRepo.findByEmail( credentials.getEmail() ).orElse(null);
        if (v != null) {
            String passwordEncoded = securityUtils.encryptPassword( credentials.getPassword() );
            if (BCrypt.checkpw(credentials.getPassword(),passwordEncoded)) {
                return securityUtils.createToken(v);
            }
        }
        return "BAD CREDENTIALS";
    }
}
