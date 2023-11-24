package com.Clone.LeBonCoin.Repository;

import com.Clone.LeBonCoin.Entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AnnouncementRepo extends CrudRepository<Announcement,Integer> {
    @Query("SELECT a FROM Announcement a WHERE a.visitor = ?1")
    Page<Announcement> getAnnouncementsByVisitorId(int visitorId, Pageable pageable);

    @Query(nativeQuery = true , value = "SELECT * FROM announcement ORDER BY price DESC fetch first 9 rows only")
    List<Announcement> getWelcomePageAnnounements();
}
