package com.example.webdev.configs;

import com.example.webdev.filters.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@CrossOrigin
public class WebSecurityConfiguration {



//  @Bean
//  public UserDetailsService userDetailsService() {
//    return new ShopmeUserDetailsService();
//  }


  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtFilter jwtFilter;


  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider
            = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    return  provider;
  }


  @Bean
  public AuthenticationManager authenticationManager(
          AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http.authenticationProvider(authenticationProvider());
      http.csrf()
              .disable()
              .authorizeHttpRequests()
              .requestMatchers("/authenticate/*", "/error").permitAll()
              .requestMatchers("/home/*").hasAuthority("USER")
              .requestMatchers("/admin/*").hasAuthority("ADMIN")
              .anyRequest()
              .authenticated().and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//              .httpBasic();

//    http.authorizeRequests().requestMatchers("/login").permitAll()
//            .requestMatchers("/users/**", "/settings/**").hasAuthority("Admin")
//            .hasAnyAuthority("Admin", "Editor", "Salesperson")
//            .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
//            .anyRequest().authenticated()
//            .and().formLogin()
//            .loginPage("/login")
//            .usernameParameter("email")
//            .permitAll()
//            .and()
//            .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
//            .and()
//            .logout().permitAll();

//    http.headers().frameOptions().sameOrigin();

    return http.build();
  }


  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/images/**");
//            .ignoring()
//            .requestMatchers("/", "/images/**", "/js/**", "/webjars/**");
//            . antMatchers("/images/**", "/js/**", "/webjars/**");
  }

}

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//            .antMatchers("/")
//            .permitAll()
//            .antMatchers("/home")
//            .hasAuthority("USER")
//            .antMatchers("/admin")
//            .hasAuthority("ADMIN")
//            .anyRequest()
//            .authenticated()
//            .and()
//            .httpBasic();
//  }
//
//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
