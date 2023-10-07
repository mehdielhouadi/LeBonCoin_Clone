package com.Clone.LeBonCoin.Controller;

import com.Clone.LeBonCoin.Entity.SecurityUtils;
import com.Clone.LeBonCoin.Entity.Visitor;
import com.Clone.LeBonCoin.Repository.AnnouncementRepo;
import com.Clone.LeBonCoin.Repository.RoleRepo;
import com.Clone.LeBonCoin.Repository.VisitorRepo;
import com.Clone.LeBonCoin.Service.VisitorService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    SecurityUtils sUtils;
    VisitorRepo vRepo;
    VisitorService vService;
    AnnouncementRepo aRepo;
    @Autowired
    RoleRepo rRepo;
    @Autowired
    public AdminController(SecurityUtils sUtils, VisitorRepo vRepo, VisitorService vService, AnnouncementRepo aRepo){
        this.sUtils = sUtils;
        this.vRepo = vRepo;
        this.vService = vService;
        this.aRepo = aRepo;
    }


    @GetMapping("/{nbOcc}")
    public Page<Visitor> getAllUsers(@PathVariable("nbOcc") int nbOccurrences,@RequestParam("page") int pageNb){

        Page<Visitor> page = vService.usersPage(nbOccurrences,pageNb);
        for (Visitor v : page) {
            v.getRoles().addAll( rRepo.findRoleByUserId(v.getId()));
        }
        return page;
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<HttpStatus> AddAdmin(@RequestBody Visitor admin) {
            vRepo.save(admin);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathParam("{id}") int id)
    {
        vRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<HttpStatus> deleteAnnouncement(@PathParam("{id}") int id)
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
