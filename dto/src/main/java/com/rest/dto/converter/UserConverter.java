package com.rest.dto.converter;

import com.rest.dto.dto.UserRequestDto;
import com.rest.dto.model.User;
import java.time.LocalDate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserRequestDto, User> {

    @Override
    public User convert(UserRequestDto userRequestDto) {
        return User.builder()
                .id(userRequestDto.getId())
                .name(userRequestDto.getName())
                .surname(userRequestDto.getSurname())
                .birthday(LocalDate.parse(userRequestDto.getBirthday()))
                .build();
    }
}
