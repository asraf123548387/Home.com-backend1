package com.example.demo.config;

import com.example.demo.Service.UserDetailsInfoService;
import com.example.demo.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService()
    {
        return new UserDetailsInfoService();
    }
        @Autowired
    private JwtAuthFilter authFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/user/profile/**") .authenticated();
                    auth.requestMatchers("/img/**","/user/**","/verify","/chat").permitAll();
                    auth.requestMatchers("/saveUser", "/", "/login","/verifyOtp","/forgotPassword","/verifyForgotPassword","/SAdmin/addAdmin").permitAll();
                    auth.requestMatchers("/admin/**").hasAnyRole("ADMIN", "SADMIN") ;
                    auth.requestMatchers("/SAdmin/**").hasRole("SADMIN");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                  .build();

    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
      @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception
  {
      return config.getAuthenticationManager();
  }
}
