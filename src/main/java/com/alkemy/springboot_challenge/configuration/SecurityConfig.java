package com.alkemy.springboot_challenge.configuration;

import com.alkemy.springboot_challenge.filters.JwtRequestFilter;
import com.alkemy.springboot_challenge.services.ChallengeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ChallengeUserDetailsService userDetailsService;
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Hay que habilitar CSRF antes de mandar a producción
        http.csrf().disable().authorizeRequests()
                /*
                 ----------------------------EN CASO DE USAR ROLES------------------------------
                .antMatchers(HttpMethod.GET, "/characters", "/movies", "/genre").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/characters", "/movies", "/genre").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/characters", "/movies", "/genre").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/characters", "/movies", "/genre").hasRole("USER")*/
                .antMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .antMatchers(HttpMethod.POST,"/auth/login").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
