package com.Clone.LeBonCoin.Controller;

import com.Clone.LeBonCoin.Entity.Announcement;
import com.Clone.LeBonCoin.Repository.AnnouncementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/announcements")
@CrossOrigin("http://localhost:4200")
public class AnnouncementController {

    @Autowired
    private AnnouncementRepo aRepo;

    @GetMapping
    public List<Announcement> getWelcomePageAnnouncements(){
        return aRepo.getWelcomePageAnnounements();
    }

}