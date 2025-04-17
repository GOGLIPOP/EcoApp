package com.tpp.EcoApp.Security;

import com.tpp.EcoApp.Models.User;
import com.tpp.EcoApp.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByLogin(login);
        System.out.println("Введенй логін: " + login);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found: " + login));

        return new CustomUserDetails(user);  // Використовуємо CustomUserDetails для повернення користувача
    }
}
