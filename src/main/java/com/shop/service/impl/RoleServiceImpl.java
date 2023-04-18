package com.shop.service.impl;

import com.shop.repository.RoleRepository;
import com.shop.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
   private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void addUserRole(String userID, int roleID) {
        roleRepository.addUserRole(userID,roleID);
    }
}
