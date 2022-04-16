package com.alkemy.springboot_challenge.controller;

import com.alkemy.springboot_challenge.entities.UserEntity;
import com.alkemy.springboot_challenge.repositories.UserRepository;
import com.alkemy.springboot_challenge.services.ChallengeUserDetailsService;
import com.alkemy.springboot_challenge.services.JWTUtil;
import com.alkemy.springboot_challenge.services.SendGridService;
import com.alkemy.springboot_challenge.utils.AuthenticationRequest;
import com.alkemy.springboot_challenge.utils.AuthenticationResponse;
import com.alkemy.springboot_challenge.utils.SendGridMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    ChallengeUserDetailsService userDetailsService;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    SendGridService sendGridService;

    /* Método sólo para confirmar que el USErREPOSITORY anda bien y devuelve el pass y el rol de la DB.
    @GetMapping("/auth/getUser/{username}")
    public UserEntity getUser(@PathVariable String username){
        return  userRepository.findByUsername(username)
                                        .orElseThrow(()-> new UsernameNotFoundException("Not found username: " + username));
    }/*/

    @PostMapping("/auth/register")
    public UserEntity registerUser(@RequestBody UserEntity userRecieved) throws IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userRecieved.setPassword(bCryptPasswordEncoder.encode(userRecieved.getPassword()));

        // No puedo testear esto porque SendGrid no me ha activado la cuenta.
        SendGridMail sendGridMail = new SendGridMail(userRecieved.getEmail());
        sendGridService.sendRequest(sendGridMail);


        return userRepository.save(userRecieved);
    }
    @PostMapping("auth/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password " + e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
