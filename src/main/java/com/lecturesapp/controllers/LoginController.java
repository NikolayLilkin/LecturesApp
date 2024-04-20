package com.lecturesapp.controllers;

import com.lecturesapp.dtos.LoginDTO;
import com.lecturesapp.dtos.LoginResponseDTO;
import com.lecturesapp.dtos.RegisterDTO;
import com.lecturesapp.dtos.TokenDTO;
import com.lecturesapp.models.Role;
import com.lecturesapp.models.UserEntity;
import com.lecturesapp.repository.RolesRepository;
import com.lecturesapp.repository.UserRepository;
import com.lecturesapp.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtGenerator jwtGenerator;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RolesRepository rolesRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }
        else {
            UserEntity user = new UserEntity();
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            if (rolesRepository.findByName("USER").isEmpty()) {
                rolesRepository.save(new Role("USER"));
            }
            Role roles = rolesRepository.findByName("USER").get();
            user.setRoles(Collections.singletonList(roles));
            userRepository.save(user);
            return new ResponseEntity<>("User registered success", HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setMessage("User signed successfully");
        loginResponseDTO.setToken(token);
        loginResponseDTO.setSuccess(true);

        UserEntity user = userRepository.findByUsername(loginDTO.getUsername()).orElseThrow();
        loginResponseDTO.setUsername(user.getUsername());
        loginResponseDTO.setPassword(user.getPassword());

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/getToken")
    public String getToken(@RequestBody TokenDTO token) {
        return jwtGenerator.getUsernameFromJWT(token.getToken());
    }
}