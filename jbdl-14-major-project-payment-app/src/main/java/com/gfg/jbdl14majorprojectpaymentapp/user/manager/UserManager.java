package com.gfg.jbdl14majorprojectpaymentapp.user.manager;

import com.gfg.jbdl14majorprojectpaymentapp.exception.NotFoundException;
import com.gfg.jbdl14majorprojectpaymentapp.user.model.SignUp;
import com.gfg.jbdl14majorprojectpaymentapp.user.model.UserResponse;

public interface UserManager {
    void create(SignUp signUpRequest);
    UserResponse get(String username) throws NotFoundException;
}
