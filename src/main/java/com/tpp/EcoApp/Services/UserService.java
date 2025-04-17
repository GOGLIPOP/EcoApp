package com.tpp.EcoApp.Services;

import com.tpp.EcoApp.Models.User;
import com.tpp.EcoApp.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String login, String rawPassword) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("USER");
        System.out.println("НОВИЙ ЮЗЕР: ");
        System.out.println("Логін: " + login);
        System.out.println("Пароль: " + rawPassword);
        System.out.println("Хеш пароля: " + passwordEncoder.encode(rawPassword));
        System.out.println("Роль: " + user.getRole());
        userRepository.save(user);
    }
}
