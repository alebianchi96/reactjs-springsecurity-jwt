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

    private static final UserEntity DEFAULT_USER = new UserEntity("ale", "ale");

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
     * Il default user per questa app di test è
     * { username:"ale", password:"ale" }
     * 
     */
    public void createDefaultUser() {

        // controllo se esiste un utente username:"ale"
        UserEntity user = userDao.findByUsername(DEFAULT_USER.getUsername());

        // se non esiste lo genero
        if (user == null) {
            user = new UserEntity(DEFAULT_USER.getUsername(), DEFAULT_USER.getPassword());
            this.saveUser(user);
        }

        // scrivo nel log le indicazioni riguardo il default user da usare
        log.info("");
        log.info(String.format("L'utente di default per questa applicazione è { username:'%s', password:'%s' }",
                DEFAULT_USER.getUsername(), DEFAULT_USER.getPassword()));
        log.info("");

    }

}
