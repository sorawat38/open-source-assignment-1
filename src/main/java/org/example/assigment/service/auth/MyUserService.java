package org.example.assigment.service.auth;

import org.example.assigment.dto.auth.RegisterResponseDTO;
import org.example.assigment.dto.auth.UpdateUserResponseDTO;
import org.example.assigment.model.auth.MyUser;
import org.example.assigment.model.auth.Role;
import org.example.assigment.repository.auth.MyUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    public RegisterResponseDTO saveUser(String username, String password) {

        // create a new user
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(Role.MEMBER.name()); // default role

        MyUser savedUser = myUserRepository.save(user);
        return new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }

    public RegisterResponseDTO saveUser(String username, String password, String role) {

        // create a new user
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(role);

        MyUser savedUser = myUserRepository.save(user);
        return new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }

    public UpdateUserResponseDTO updateUser(Long id, String username, String password, String role) {
        Optional<MyUser> myUser = myUserRepository.findById(id);
        if (myUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }

        MyUser user = myUser.get();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(role);

        MyUser updatedUser = myUserRepository.save(user);
        return new UpdateUserResponseDTO(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getRole()
        );
    }


    public void deleteLibrarian(Long id) {
        MyUser user = myUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        if (Arrays.stream(user.getRoles()).noneMatch(role -> role.equals(Role.LIBRARIAN.name()))) {
            throw new IllegalStateException("User is not a librarian");
        }

        myUserRepository.delete(user);
    }
}
