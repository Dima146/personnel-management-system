package com.company.managementsystem.security;

import com.company.managementsystem.entity.User;
import com.company.managementsystem.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private UserService userService;

    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        HttpSession session = request.getSession();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

        Optional<User> user = userService.findByUsername(username);

        session.setAttribute("user", user.get());

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/user/profile");

    }
}
