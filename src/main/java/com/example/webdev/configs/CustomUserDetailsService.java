package com.example.webdev.configs;

import com.example.webdev.model.User;
import com.example.webdev.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByUsername(username);

    if(Objects.isNull(user)){
      throw new UsernameNotFoundException("Unable to find user");
    }

    return new CustomUserDetails(user);

  }
}
