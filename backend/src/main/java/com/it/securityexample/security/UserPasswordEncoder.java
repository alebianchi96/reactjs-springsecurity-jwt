package com.it.securityexample.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordEncoder {

    private PasswordEncoder passEncoder;

    public PasswordEncoder getPassEncoder() {
        if (this.passEncoder == null) {
            this.passEncoder = new BCryptPasswordEncoder(12);
        }
        return this.passEncoder;
    }

}
