package org.example.assigment.service.auth;

import org.example.assigment.dto.RegisterRequestDTO;
import org.example.assigment.dto.RegisterResponseDTO;
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

    public RegisterResponseDTO saveUser(RegisterRequestDTO request) {

        // create a new user
        MyUser user = new MyUser();
        user.setUsername(request.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(request.getRole());

        MyUser savedUser = myUserRepository.save(user);
        return new RegisterResponseDTO(
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }

}
