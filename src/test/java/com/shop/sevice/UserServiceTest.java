package com.shop.sevice;

import com.shop.model.Role;
import com.shop.model.User;
import com.shop.repository.RoleRepository;
import com.shop.repository.UserRepository;
import com.shop.service.impl.UserServiceImpl;
import com.shop.util.DateConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    private User user;


    @Test
    public void shouldCompareUserWhenLoadUserByEmail() {
        user = getMockUser();
        String email = user.getEmail();

        when(userRepository.findUserByEmail(email)).thenReturn(user);

        User actualUser = userService.findUserByEmail(email);

        assertEquals(actualUser, user);

        verify(userRepository, atLeastOnce()).findUserByEmail(user.getEmail());

    }

    @Test
    public void shouldCompareUserWhenLoadUserByUserName() {
        user = getMockUser();
        String email = user.getEmail();

        when(userRepository.findUserByEmail(email)).thenReturn(user);

        User actualUser = (User) userService.loadUserByUsername(email);

        assertEquals(actualUser, user);

        verify(userRepository, atLeastOnce()).findUserByEmail(user.getEmail());

    }

    @Test
    public void shouldVerifyCallOfUserRepositoryMethodWhenUpdateUserFailedAttempts() {
        user = getMockUser();

        userService.updateUserFailedAttempts(user.getId(), user.getFailedAttempts());

        verify(userRepository, atLeastOnce()).updateUserFailedAttempts(user.getFailedAttempts(), user.getId());
    }

    @Test
    public void shouldVerifyCallOfUpdateUserFailedAttemptsAndBanDateMethodWhenUserFailedAttemptsEqualsMaxNumber() {
        user = getMockUser();
        int maxFailedAttempts = 3;
        int banTime = 1;
        user.setFailedAttempts(maxFailedAttempts);

        userService.updateUserFailedAttemptsAndBanDate(user, maxFailedAttempts, banTime);

        verify(userRepository, atLeastOnce()).updateUserFailedAttemptsAndBanDate(0,
                DateConverter.convertLongToTimeStamp(System.currentTimeMillis() + banTime), user.getId());
        verify(userRepository, never()).updateUserFailedAttempts(anyInt(), anyString());
    }

    @Test
    public void shouldVerifyCallOfUpdateUserFailedAttemptsWhenUserFailedAttemptsLessThanMaxNumber() {
        user = getMockUser();
        int maxFailedAttempts = 3;
        int banTime = 1;

        userService.updateUserFailedAttemptsAndBanDate(user, maxFailedAttempts, banTime);

        verify(userRepository, never()).updateUserFailedAttemptsAndBanDate(0,
                DateConverter.convertLongToTimeStamp(System.currentTimeMillis() + banTime), user.getId());
        verify(userRepository, atLeastOnce()).updateUserFailedAttempts(anyInt(), anyString());
    }

    @Test
    public void shouldVerifyCallSaveMethodAndAddUserRoleMethodWhenCreateUser() {
        user = getMockUser();
        int userRole = 1;

        userService.createUser(user, userRole);

        verify(userRepository, atLeastOnce()).save(user);
        verify(roleRepository, atLeastOnce()).addUserRole(user.getId(), userRole);
    }

    private User getMockUser() {
        User user = new User();
        user.setId("1");
        user.setFailedAttempts(0);
        user.setName("User");
        user.setEmail("user@gmail.com");
        user.setSurname("User");
        user.setPassword("password.form.input");
        user.setBanDate("2022-12-21 00:00");
        Set roleSet = new LinkedHashSet<Role>();
        Role role1 = new Role();
        role1.setId(0);
        role1.setName("ADMIN");
        Role role2 = new Role();
        role1.setId(1);
        role1.setName("USER");
        roleSet.add(role1);
        roleSet.add(role2);
        user.setRoles(roleSet);
        return user;
    }
}
