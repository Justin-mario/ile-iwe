package com.ileiwe.security.config;

import com.ileiwe.security.user.LearningPartyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder  passwordEncoder;

    private final LearningPartyServiceImpl  userDetails;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder, LearningPartyServiceImpl userDetails){
        this.passwordEncoder = passwordEncoder;
        this.userDetails = userDetails;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http    .csrf ().disable ()
                .authorizeRequests ()
                .antMatchers ( HttpMethod.POST, "/**" ).permitAll ()
                .antMatchers ( HttpMethod.GET, "/**" ).permitAll ()
                .antMatchers ( HttpMethod.DELETE,"/course/{id}" ).permitAll ()
                .antMatchers ( HttpMethod.PATCH,"/**" ).permitAll ()
                .anyRequest ()
                .authenticated ()
                .and ()
                .addFilter (new JWTAuthenticationFilter (authenticationManager ())  )
                .addFilter ( new JWTAuthorizationFilter (authenticationManager ()  ) )
                .exceptionHandling ()
                .and ().sessionManagement ().sessionCreationPolicy ( SessionCreationPolicy.STATELESS );
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService ( userDetails ).passwordEncoder ( passwordEncoder );
    }


}
