package com.Clone.LeBonCoin.Service;

import com.Clone.LeBonCoin.Entity.Announcement;
import com.Clone.LeBonCoin.Entity.Role;
import com.Clone.LeBonCoin.Entity.RoleEnum;
import com.Clone.LeBonCoin.Entity.Visitor;
import com.Clone.LeBonCoin.Repository.VisitorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {
    private VisitorRepo visitorRepo;
    @Autowired
    public VisitorService(VisitorRepo visitorRepo){
        this.visitorRepo = visitorRepo;
    }

    public boolean isAdmin(Visitor v){
        boolean isVisitorAdmin = false;
        for(Role role : visitorRepo.findById(v.getId()).orElseThrow().getRoles()){
            if(role.getRole() == RoleEnum.ADMIN){
                isVisitorAdmin = true;
            }
        }
        return isVisitorAdmin;
    }

    public void makeAdmin(Visitor v){
        v.getRoles().add(new Role(RoleEnum.ADMIN));
    }

    public void createAnnouncement(int userId, Announcement ann){
        Visitor visitor = visitorRepo.findById(userId).orElseThrow();
        visitor.getAnnouncements().add(ann);
    }

    public Page<Visitor> usersPage(int nbOccurrences, int pageNb){
        Sort sort = Sort.by("subscriptionDate").ascending();
        Pageable pageable = PageRequest.of(pageNb, nbOccurrences, sort);
        return visitorRepo.findAll(pageable);
    }
}
