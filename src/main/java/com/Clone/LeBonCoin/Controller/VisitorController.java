package com.Clone.LeBonCoin.Controller;

import com.Clone.LeBonCoin.Entity.Announcement;
import com.Clone.LeBonCoin.Repository.AnnouncementRepo;
import com.Clone.LeBonCoin.Repository.VisitorRepo;
import com.Clone.LeBonCoin.Service.AnnouncementService;
import com.Clone.LeBonCoin.Service.VisitorService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class VisitorController {
    VisitorService vService;
    AnnouncementRepo aRepo;
    AnnouncementService aService;
    VisitorRepo vRepo;
    @Autowired
    public VisitorController(VisitorService vService, AnnouncementRepo aRepo,
                             VisitorRepo vRepo, AnnouncementService aService) {
        this.vService = vService;
        this.vRepo = vRepo;
        this.aRepo = aRepo;
        this.aService = aService;
    }

    @GetMapping("/{id}/announcements/pageNb")
    public Page<Announcement> getAnnouncements(@PathVariable("id") int id, @RequestParam("pageNb") int pageNb){
        return aService.userAnnouncements(id, 10,pageNb);
    }

    @PostMapping("/{id}/announcements/create")
    public void createAnnouncement(@PathVariable("id") int id, @RequestBody Announcement ann){
        aRepo.save(ann);
    }

    @PutMapping("/{id}/updateAnnouncement")
    public void updateAnnouncement(@PathVariable("id") int id, @RequestBody Announcement ann){
        aRepo.save(ann);
    }

    @DeleteMapping("/{id}/deleteAnnouncement")
    public void deleteAnnouncement(@PathVariable("id") int id, @PathParam("id") int annId){
        aRepo.deleteById(annId);
    }
}
