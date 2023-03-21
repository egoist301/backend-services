package com.rest.serviceapi.service;

import com.rest.dto.dto.UserRequestDto;
import com.rest.dto.dto.UserResponseDto;
import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUser(Long userid);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(UserRequestDto userRequestDto);

    void deleteUser(Long userid);
}
