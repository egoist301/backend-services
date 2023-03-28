package com.security.service;

import com.security.handler.CustomAuthenticationFailureHandler;
import com.security.model.User;
import com.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String USER_NOT_FOUND = "User Not Found";
    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        } else if (loginAttemptService.isBlocked(username)) {
            user.setEnabled(false);
            user.setBlockingTime(loginAttemptService.getCachedValue(username).getBlockedTimestamp());
            userRepository.save(user);
            throw new LockedException(CustomAuthenticationFailureHandler.USER_IS_BLOCKED);
        }
        return UserDetailsImpl.build(user);
    }
}
