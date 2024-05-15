package com.it.securityexample.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.it.securityexample.security.UserPasswordEncoder;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPasswordEncoder upEncoder;

    public UserEntity saveUser(UserEntity user) {
        user.setPassword(upEncoder.getPassEncoder().encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Error 404");
        } else {
            return new UserPrincipalDTO(user);
        }
    }

    /**
     * In that example, at the start of the application a DEFAULT_USER will
     * be created in database in order to allow developers to test
     * 
     * { username:"ale", password:"ale" }
     * 
     */
    public void createDefaultUser() {

        final UserEntity defaultUser = new UserEntity("ale", "ale");

        // controllo se esiste un utente username:"ale"
        UserEntity user = userDao.findByUsername(defaultUser.getUsername());

        // se non esiste lo genero
        if (user == null) {
            user = new UserEntity(defaultUser.getUsername(), defaultUser.getPassword());
            this.saveUser(user);
        }

        // scrivo nel log le indicazioni riguardo il default user da usare
        log.info("");
        log.info(String.format(
                "DEFAULT_USER for this application is { username:'%s', password:'%s' }",
                defaultUser.getUsername(),
                defaultUser.getPassword()));
        log.info("");

    }

}
