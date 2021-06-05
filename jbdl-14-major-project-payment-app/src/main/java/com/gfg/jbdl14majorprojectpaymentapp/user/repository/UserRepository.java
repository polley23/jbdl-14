package com.gfg.jbdl14majorprojectpaymentapp.user.repository;

import com.gfg.jbdl14majorprojectpaymentapp.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
