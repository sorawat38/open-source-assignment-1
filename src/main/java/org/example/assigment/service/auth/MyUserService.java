package org.example.assigment.service.auth;

import jakarta.persistence.EntityNotFoundException;
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

    /**
     * Creates and saves a new user with the default MEMBER role.
     * <p>
     * This method is intended for normal user registration where the role
     * is automatically set to MEMBER. The password is securely encoded before being saved.
     *
     * @param username the username of the new user
     * @param password the plain-text password to be encoded and stored
     * @return a RegisterResponseDTO containing the ID, username, and assigned role of the new user
     */
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

    /**
     * Creates and saves a new user with the specified role.
     * <p>
     * This method is typically used by an administrator to register users with a
     * specific role such as ADMIN, LIBRARIAN, or MEMBER. The password is encoded
     * before saving. Returns a DTO containing the newly registered user's information.
     *
     * @param username the username of the new user
     * @param password the plain-text password to be encoded and saved
     * @param role     the role to assign to the user (e.g., "ADMIN", "LIBRARIAN", "MEMBER")
     * @return a RegisterResponseDTO containing the ID, username, and role of the created user
     */
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

    /**
     * Deletes a user only if their role is LIBRARIAN.
     *
     * @param id the ID of the user to delete
     * @throws EntityNotFoundException if the user does not exist
     * @throws IllegalStateException   if the user is not a librarian
     */
    public void deleteLibrarian(Long id) {
        MyUser user = myUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (Arrays.stream(user.getRoles()).noneMatch(role -> role.equals(Role.LIBRARIAN.name()))) {
            throw new IllegalStateException("User is not a librarian");
        }

        myUserRepository.delete(user);
    }

    /**
     * Creates a new user with the LIBRARIAN role.
     * <p>
     * This method is used when an admin registers a new librarian.
     *
     * @param username the username of the new librarian
     * @param password the plain-text password to encode and store
     * @return RegisterResponseDTO containing the newly created librarian's info
     */
    public RegisterResponseDTO createLibrarian(String username, String password) {
        return saveUser(username, password, Role.LIBRARIAN.name());
    }

    /**
     * Updates the username and password of an existing librarian.
     * <p>
     * This method ensures the target user has the LIBRARIAN role before updating.
     *
     * @param id       the ID of the librarian to update
     * @param username the new username
     * @param password the new plain-text password
     * @return UpdateUserResponseDTO with updated user information
     * @throws EntityNotFoundException if the user is not found
     * @throws IllegalStateException   if the user is not a librarian
     */
    public UpdateUserResponseDTO updateLibrarian(Long id, String username, String password) {
        Optional<MyUser> myUser = myUserRepository.findById(id);
        if (myUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        MyUser user = myUser.get();

        if (Arrays.stream(user.getRoles()).noneMatch(role -> role.equals(Role.LIBRARIAN.name()))) {
            throw new IllegalStateException("User is not a librarian");
        }

        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        MyUser updatedUser = myUserRepository.save(user);
        return new UpdateUserResponseDTO(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getRole()
        );
    }
}
