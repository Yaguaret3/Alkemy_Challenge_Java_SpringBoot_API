package com.alkemy.springboot_challenge.services;

import com.alkemy.springboot_challenge.entities.UserEntity;
import com.alkemy.springboot_challenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChallengeUserDetailsService implements UserDetailsService {

   @Autowired
   UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new UsernameNotFoundException("Not found username: "+username));

        return new ChallengeUserDetails(user);
    }
}
