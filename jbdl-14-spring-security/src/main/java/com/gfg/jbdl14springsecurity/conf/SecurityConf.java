package com.gfg.jbdl14springsecurity.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        auth
                .userDetailsService(userDetailsService)
                .and()
                .inMemoryAuthentication()
                .withUser("admin")
                .authorities("admin")
                .password(passwordEncoder.encode("password"));
    }
    //Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .disable()
                .csrf()
                .disable()
                .httpBasic();
//                .and()
//                .authorizeRequests()
//                .antMatchers("/home")
//                .authenticated()
//                .antMatchers("/guests")
//                .permitAll()
//                .antMatchers("/admin")
//                .hasAnyAuthority("admin");
    }
}
