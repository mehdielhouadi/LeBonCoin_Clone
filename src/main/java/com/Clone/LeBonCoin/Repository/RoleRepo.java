package com.Clone.LeBonCoin.Repository;

import com.Clone.LeBonCoin.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    @Query("SELECT role FROM Visitor v JOIN v.roles role WHERE v.id = ?1  ")
    Set<Role> findRoleByUserId(int userId);
}
