package com.Clone.LeBonCoin.Controller;

import com.Clone.LeBonCoin.Entity.SecurityUtils;
import com.Clone.LeBonCoin.Entity.Visitor;
import com.Clone.LeBonCoin.Repository.AnnouncementRepo;
import com.Clone.LeBonCoin.Repository.VisitorRepo;
import com.Clone.LeBonCoin.Service.VisitorService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {
    SecurityUtils sUtils;
    VisitorRepo vRepo;
    VisitorService vService;
    AnnouncementRepo aRepo;
    @Autowired
    public AdminController(SecurityUtils sUtils, VisitorRepo vRepo, VisitorService vService, AnnouncementRepo aRepo){
        this.sUtils = sUtils;
        this.vRepo = vRepo;
        this.vService = vService;
        this.aRepo = aRepo;
    }


    @GetMapping("/getUsers")
    public ResponseEntity<Page<Visitor>> getAllUsers(@RequestHeader (name="Authorization") String token,
                                                     int nbOccurrences, int pageNb){

        return new ResponseEntity<>(vService.usersPage(nbOccurrences,pageNb), HttpStatus.OK);
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<HttpStatus> AddAdmin(@RequestHeader (name="Authorization") String token,
                                               @RequestBody Visitor admin) {
        if (sUtils.isAdminToken(token)){
            vService.makeAdmin(admin);
            vRepo.save(admin);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@RequestHeader (name="Authorization") String token,
                                                 @PathParam("{id}")int id)
    {
        vRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<HttpStatus> deleteAnnouncement(@RequestHeader (name="Authorization") String token,
                                                         @RequestParam int id)
    {
        aRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody Visitor visitor){
        vRepo.save(visitor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
