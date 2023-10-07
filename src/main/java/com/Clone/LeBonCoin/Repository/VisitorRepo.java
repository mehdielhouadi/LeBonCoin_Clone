package com.Clone.LeBonCoin.Repository;

import com.Clone.LeBonCoin.Entity.Role;
import com.Clone.LeBonCoin.Entity.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VisitorRepo extends CrudRepository<Visitor,Integer> {
    Optional<Visitor> findByEmail(String email);

    Page<Visitor> findAll(Pageable pageable);

    @Query("SELECT role.role FROM Role role JOIN role.visitors visitor WHERE visitor.email = ?1 and role.role = ?2 ")
    Optional<Role> findRoleByEmailAndType(String email, String role);

}
