package com.shop.repository;

import com.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User user SET user.failedAttempts =?1 WHERE user.id=?2")
    void updateUserFailedAttempts(int failedAttempts, String userID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User user SET user.failedAttempts =?1 , user.banDate=?2 WHERE user.id=?3")
    void updateUserFailedAttemptsAndBanDate(int failedAttempts, String banDate, String userID);

}
