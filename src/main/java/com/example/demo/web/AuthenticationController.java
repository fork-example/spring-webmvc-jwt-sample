package com.example.demo.web;

import com.example.demo.config.component.TokenProvider;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.model.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class AuthenticationController {

//    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    private final UserRepository users;

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody AuthenticationRequest data) {

        try {
            String username = data.getUsername();
            this.users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            String token = tokenProvider.createToken(username);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
