package com.it.securityexample.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.securityexample.security.JwtService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody UserEntity user) {
        if (userService.saveUser(user) != null) {
            return ResponseEntity.ok("User Registered Successfully");
        }
        return new ResponseEntity<>("Oops! User not registered", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity user) {

        try {

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword()));

            if (auth.isAuthenticated()) {
                return ResponseEntity.ok(jwtService.generateToken(user.getUsername()));
            }

        } catch (Exception e) {
            //
        }

        return new ResponseEntity<>("Username o password errati", HttpStatus.BAD_REQUEST);

    }

}
