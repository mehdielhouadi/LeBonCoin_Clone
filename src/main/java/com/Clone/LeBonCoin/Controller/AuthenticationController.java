package com.Clone.LeBonCoin.Controller;

import com.Clone.LeBonCoin.Entity.*;
import com.Clone.LeBonCoin.Repository.VisitorRepo;
import com.Clone.LeBonCoin.Service.VisitorService;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.EncoderException;
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
    public void CreateAccount(@RequestBody Visitor v) throws EncoderException {
        String encodedPassword = securityUtils.encryptPassword(v.getPassword());
        v.setPassword( encodedPassword );
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleEnum.USER));
        v.setRoles(roles);
        visitorRepo.save(v);
    }

    @PostMapping("/login")
    public String login(@RequestBody Credentials credentials) throws EncoderException {
        Visitor v = visitorRepo.findByEmail( credentials.getEmail() ).orElse(null);
        if (v != null) {
            String passwordEncoded = securityUtils.encryptPassword( credentials.getPassword() );
            if (v.getPassword().equals(passwordEncoded)) {
                return securityUtils.createToken(v);
            }
        }
        return "BAD CREDENTIALS";
    }
}
