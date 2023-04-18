package com.shop.service.impl;

import com.shop.model.User;
import com.shop.repository.RoleRepository;
import com.shop.repository.UserRepository;
import com.shop.util.DateConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(s);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    public void updateUserFailedAttempts(String id, int failedAttempts) {
        userRepository.updateUserFailedAttempts(failedAttempts, id);
    }

    public void updateUserFailedAttemptsAndBanDate(User user, int maxFailedAttempts, long banTime) {
        user.setFailedAttempts(user.getFailedAttempts() + 1);
        if (user.getFailedAttempts() >= maxFailedAttempts) {
            userRepository.updateUserFailedAttemptsAndBanDate(0,
                    DateConverter.convertLongToTimeStamp(System.currentTimeMillis() + banTime), user.getId());
        } else {
            userRepository.updateUserFailedAttempts(user.getFailedAttempts(), user.getId());
        }
    }

    @Transactional
    public void createUser(User user, int roleId) {
        userRepository.save(user);
        roleRepository.addUserRole(user.getId(), roleId);
    }
}
