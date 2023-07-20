package com.company.managementsystem.controller;

import com.company.managementsystem.ThymeleafdemoApplication;
import com.company.managementsystem.entity.User;
import com.company.managementsystem.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = ThymeleafdemoApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void RegistrationController_RegisterUser_ReturnSuccess() throws Exception {

        User user = new User("username", "password", true,
                "First Name", "Last Name", "email@mail.com");

        doNothing().when(userService).saveUser(user);

        MvcResult mvcResult = mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(user)))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "registration-form");

    }

    @Test
    public void RegistrationController_RegistrationForm_ReturnForm() throws Exception {

        MvcResult  mvcResult = mockMvc.perform(get("/user/registrationForm")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "registration-form");
    }
}
