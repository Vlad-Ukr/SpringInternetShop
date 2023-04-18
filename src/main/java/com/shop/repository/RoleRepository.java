package com.shop.repository;

import com.shop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users_roles VALUES(?1,?2)", nativeQuery = true)
    void addUserRole(String userID, int roleID);
}
