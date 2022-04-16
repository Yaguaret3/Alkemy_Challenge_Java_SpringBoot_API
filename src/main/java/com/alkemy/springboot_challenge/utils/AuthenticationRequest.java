package com.alkemy.springboot_challenge.utils;

import lombok.Getter;
import lombok.Setter;

public class AuthenticationRequest {

    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;

    public AuthenticationRequest(String username, String password){
        this.username = username;
        this.password = password;
    }
    public AuthenticationRequest(){
    }



}
