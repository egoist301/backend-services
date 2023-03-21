package com.rest.dto.converter;

import com.rest.dto.dto.SubscriptionResponseDto;
import com.rest.dto.model.Subscription;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionResponseConverter implements Converter<Subscription, SubscriptionResponseDto> {

    @Override
    public SubscriptionResponseDto convert(Subscription subscription) {
        return SubscriptionResponseDto.builder()
                .id(subscription.getId())
                .userId(subscription.getUser().getId())
                .startDate(String.valueOf(subscription.getStartDate()))
                .build();
    }
}
