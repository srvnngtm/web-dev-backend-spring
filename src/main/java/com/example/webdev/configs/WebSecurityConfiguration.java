package com.example.webdev.configs;

import com.example.webdev.filters.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
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
              .requestMatchers(HttpMethod.OPTIONS,"*").permitAll()
              .requestMatchers("/authenticate/*", "/error","/no-auth/**").permitAll()
              .requestMatchers("/home/*").hasAnyAuthority("USER","TRAINER", "ADMIN")
              .requestMatchers("/admin/*").hasAuthority("ADMIN")
              .requestMatchers("/trainer/*").hasAnyAuthority("TRAINER", "ADMIN")
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
  public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:3000");
    config.addAllowedOrigin("https://fitnessappwebdevproj.netlify.app");
    // TODO : add final one here
    config.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET", "DELETE", "PUT"));
    config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
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
