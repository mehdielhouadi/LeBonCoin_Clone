package com.Clone.LeBonCoin.Repository;

import com.Clone.LeBonCoin.Entity.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VisitorRepo extends CrudRepository<Visitor,Integer> {
    Optional<Visitor> findByEmail(String email);

    Page<Visitor> findAll(Pageable pageable);
}
