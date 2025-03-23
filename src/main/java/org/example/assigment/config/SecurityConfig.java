package org.example.assigment.config;

import org.example.assigment.service.auth.JwtAuthenticationFilter;
import org.example.assigment.service.auth.MyUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final MyUserService myUserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(MyUserService myUserService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.myUserService = myUserService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // define authentication config for Spring Security
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // set user details service
        provider.setUserDetailsService(myUserService);
        // set password encoder
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


    // Security Config using filter chains
    // configure roles
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // grant authorization to users based on roles
        httpSecurity.authorizeHttpRequests(securityConfigurer ->
                securityConfigurer
                        .requestMatchers("/api/register").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        // books
                        .requestMatchers(HttpMethod.GET, "/api/books/available").permitAll() // get all available books
                        .requestMatchers(HttpMethod.POST, "/api/books").hasAnyRole("ADMIN", "LIBRARIAN")// add (create book)
                        .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN") // update book
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN") // delete book
                        // authors
                        .requestMatchers(HttpMethod.POST, "/api/authors").hasAnyRole("ADMIN", "LIBRARIAN") // add (create author)
                        .requestMatchers(HttpMethod.PUT, "/api/authors/**").hasAnyRole("ADMIN", "LIBRARIAN") // update author
                        .requestMatchers(HttpMethod.DELETE, "/api/authors/**").hasAnyRole("ADMIN", "LIBRARIAN") // delete author
                        // borrow records
                        .requestMatchers(HttpMethod.GET, "/api/borrow-records").hasRole("ADMIN") // get all borrow records
                        .requestMatchers(HttpMethod.POST, "/api/borrow-records").hasAnyRole("LIBRARIAN") // add (create borrow record)
                        .requestMatchers(HttpMethod.PUT, "/api/borrow-records/**").hasAnyRole("LIBRARIAN") // update borrow record
                        .requestMatchers(HttpMethod.PATCH, "/api/borrow-records/*/return").hasAnyRole("LIBRARIAN", "MEMBER") // return book
                        // library members
                        .requestMatchers(HttpMethod.GET, "/api/library-members").hasRole("LIBRARIAN") // get all library members
                        .requestMatchers(HttpMethod.POST, "/api/library-members/*/borrowed-books").hasRole("MEMBER") // borrow book by member
                        .requestMatchers(HttpMethod.GET, "/api/library-members/*/borrowed-books").hasAnyRole("MEMBER") // get all borrowed books by library member
        );

        httpSecurity.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
        // disable CSRF for testing purposes
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

}
