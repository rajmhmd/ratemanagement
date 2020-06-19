package com.raj.logistics.ratemanagement.config;

import static com.raj.logistics.ratemanagement.common.RateManagementConstants.ADMIN;
import static com.raj.logistics.ratemanagement.common.RateManagementConstants.USER;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${rms.localuser.userName}")
    String localUserName;

    @Value("${rms.localuser.password}")
    String localUserPassword;

    @Value("${rms.adminuser.userName}")
    String adminUserName;

    @Value("${rms.adminuser.password}")
    String adminUserPassword;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser(localUserName).password(localUserPassword).roles(USER)
                .and()
                .withUser(adminUserName).password(adminUserPassword).roles(USER, ADMIN);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

                http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/ratemanagement/**").hasRole(USER)
                .antMatchers(HttpMethod.POST, "/ratemanagement/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/ratemanagement/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/ratemanagement/**").hasRole(ADMIN)
                .and()
                .csrf().disable()
                .formLogin().disable();
    }


}
