package com.security.listener;

import com.security.repository.UserRepository;
import com.security.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final LoginAttemptService loginAttemptService;
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            if (userRepository.findByUsernameIgnoreCase(username) != null) {
                loginAttemptService.loginFailed(username);
            }
        }
    }
}
