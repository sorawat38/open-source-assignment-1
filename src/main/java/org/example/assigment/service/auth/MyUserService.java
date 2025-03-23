package org.example.assigment.service.auth;

import org.example.assigment.model.auth.MyUser;
import org.example.assigment.repository.auth.MyUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserService implements UserDetailsService {

    private MyUserRepository myUserRepository;

    public MyUserService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = myUserRepository.findByUsername(username);
        if (myUser.isPresent()) {
            MyUser user = myUser.get();
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles())
                    .build();
        }
        return null;
    }

    public MyUser saveUser(MyUser user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return myUserRepository.save(user);
    }

}
