package com.gfg.jbdl14springsecurity.manager;

import com.gfg.jbdl14springsecurity.entities.Role;
import com.gfg.jbdl14springsecurity.entities.User;
import com.gfg.jbdl14springsecurity.model.SignupRequest;
import com.gfg.jbdl14springsecurity.repository.RoleRepository;
import com.gfg.jbdl14springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username is not found"));
        return user;
    }

    public void signUp(SignupRequest signupRequest){
        Role role = roleRepository.findByRole("user").orElse(
           Role.builder().role("user").build());
        User user = User.builder().username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .roles(Arrays.asList(role))
                .build();
        userRepository.save(user);
    }
}
