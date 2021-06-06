package com.gfg.jbdl14majorprojectpaymentapp.user.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.jbdl14majorprojectpaymentapp.exception.NotFoundException;
import com.gfg.jbdl14majorprojectpaymentapp.user.entity.User;
import com.gfg.jbdl14majorprojectpaymentapp.user.model.SignUp;
import com.gfg.jbdl14majorprojectpaymentapp.user.model.UserResponse;
import com.gfg.jbdl14majorprojectpaymentapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl implements UserManager {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void create(SignUp signUpRequest) {
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .username(signUpRequest.getUsername())
                .role("user")
                .build();
        userRepository.save(user);
        try {
            kafkaTemplate.send("user",objectMapper.writeValueAsString(signUpRequest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public UserResponse get(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("user not found"));
        return UserResponse.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
