package com.ksenia.demo.service.impl;

import com.ksenia.demo.controller.UserController;
import com.ksenia.demo.model.User;
import com.ksenia.demo.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {
     @Autowired
    private UserController userController;
     @Autowired
     private MockMvc mockMvc;
    @Autowired
    private UserServiceImpl  userService;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    ApplicationContext context;


    @Test
    void findUserByLogin() {
        Mockito.doReturn(new User())
                .when(userRepository)
                .findUserByLogin("Katty");

        User userFromDb = userService.findUserByLogin("Katty");

        Assert.assertNotNull(userFromDb);
    }
    @Test
    public void loadUserByLoginFailTest() {
        User userFromDb = userService.findUserByLogin("125");

        Assert.assertNull("object should be null", userFromDb);
    }

    @Test
    void getAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));
        List<User> users = userRepository.findAll();
        Assert.assertEquals(Collections.singletonList(new User()),users);
    }


}