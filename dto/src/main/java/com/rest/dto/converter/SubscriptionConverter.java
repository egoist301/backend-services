package com.rest.dto.converter;

import com.rest.dto.dto.SubscriptionRequestDto;
import com.rest.dto.model.Subscription;
import com.rest.dto.model.User;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionConverter {
    public Subscription convert(SubscriptionRequestDto subscriptionRequestDto, User user) {
        return Subscription.builder()
                .id(subscriptionRequestDto.getId())
                .user(user)
                .build();
    }

}
