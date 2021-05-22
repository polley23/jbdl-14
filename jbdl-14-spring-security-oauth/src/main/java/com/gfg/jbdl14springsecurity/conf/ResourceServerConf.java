package com.gfg.jbdl14springsecurity.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConf extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("resource");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
              .authorizeRequests()
              .antMatchers("/home")
              .access("#oauth2.hasAnyScope('read','write')")
                .antMatchers("/admin")
                .access("#oauth2.hasAnyScope('read','write')")
                .antMatchers("/signup")
                .permitAll()
                .and()
              .csrf()
              .disable()
              .formLogin()
              .disable();
    }
}
