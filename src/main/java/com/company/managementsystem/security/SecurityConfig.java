package com.company.managementsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    private AuthenticationSuccessHandlerImpl successHandler;

    @Autowired
    public void setSuccessHandler(AuthenticationSuccessHandlerImpl successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                    configurer

                            .requestMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
                            .requestMatchers("/employees/save").hasAnyRole("MANAGER", "ADMIN")
                            .requestMatchers("/employees/delete").hasRole("ADMIN")
                            .requestMatchers("/employees/list").hasRole("EMPLOYEE")
                            .requestMatchers("/employees/search").hasRole("EMPLOYEE")
                            .requestMatchers("/user/**").permitAll()
                            .anyRequest().authenticated())

                .formLogin(form ->
                        form
                                .loginPage("/showLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                                .successHandler(successHandler))

                .logout(logout ->
                        logout.
                                permitAll()
                                .logoutSuccessUrl("/showLoginPage")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/showLoginPage" + "?logout"))

                .sessionManagement(session ->
                        session.invalidSessionUrl("/showLoginPage"))

                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"));

        return http.build();

    }
}
