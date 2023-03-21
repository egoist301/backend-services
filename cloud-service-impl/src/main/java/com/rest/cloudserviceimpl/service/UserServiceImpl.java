package com.rest.cloudserviceimpl.service;

import com.rest.dto.converter.UserConverter;
import com.rest.dto.converter.UserResponseConverter;
import com.rest.dto.dto.UserRequestDto;
import com.rest.dto.dto.UserResponseDto;
import com.rest.dto.model.User;
import com.rest.serviceapi.service.UserService;
import com.rest.servicedb.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final UserResponseConverter userResponseConverter;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(userResponseConverter::convert).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUser(Long userId) {
        return userResponseConverter.convert(findUserById(userId));
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return userResponseConverter.convert(userRepository.save(Objects.requireNonNull(userConverter.convert(userRequestDto))));
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto userRequestDto) {
        User user = findUserById(userRequestDto.getId());
        user.setName(userRequestDto.getName());
        user.setSurname(userRequestDto.getSurname());
        user.setBirthday(LocalDate.parse(userRequestDto.getBirthday()));
        return userResponseConverter.convert(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userid) {
        userRepository.delete(findUserById(userid));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with that ID: %d Not found.", id)));
    }
}
