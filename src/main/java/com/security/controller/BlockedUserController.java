package com.security.controller;

import com.security.model.User;
import com.security.repository.UserRepository;
import com.security.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class BlockedUserController {
    public static final String BLOCKED_USERS = "blockedUsers";
    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @GetMapping("/blocked")
    public String blockedUsers(Model model) {
        List<User> users = userRepository.findAll();
        Map<String, LocalDateTime> blockedUsers = users.stream()
                .filter(user -> !user.isEnabled())
                .collect(Collectors.toMap(User::getUsername, User::getBlockingTime));
        if (!blockedUsers.isEmpty()) {
            model.addAttribute(BLOCKED_USERS, blockedUsers);
        }
        return "pages/blocked";
    }

    @PostMapping("/blocked/{user}")
    public String unBlockUser(@PathVariable(value = "user") String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        user.setEnabled(true);
        user.setBlockingTime(LocalDateTime.now());
        userRepository.save(user);
        return "pages/blocked";
    }
}
