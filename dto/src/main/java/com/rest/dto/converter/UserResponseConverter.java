package com.rest.dto.converter;

import com.rest.dto.dto.UserResponseDto;
import com.rest.dto.model.User;
import java.time.LocalDate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserResponseConverter implements Converter<User, UserResponseDto> {

    @Override
    public UserResponseDto convert(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .birthday(String.valueOf(user.getBirthday()))
                .build();
    }
}
