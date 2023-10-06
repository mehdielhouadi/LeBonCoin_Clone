package com.Clone.LeBonCoin.Service;

import com.Clone.LeBonCoin.Entity.Announcement;
import com.Clone.LeBonCoin.Repository.AnnouncementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AnnouncementService {
    private AnnouncementRepo aRepo;
    @Autowired
    public AnnouncementService(AnnouncementRepo aRepo){
        this.aRepo = aRepo;
    }

    public Page<Announcement> userAnnouncements(int id, int nbOccurrences, int pageNb){
        Sort sort = Sort.by("price").ascending();
        Pageable pageable = PageRequest.of(pageNb, nbOccurrences, sort);
        return aRepo.getAnnouncementsByVisitorId(id,pageable);
    }


}
