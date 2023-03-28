package com.security.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CachedValue {

    private int attempts;
    private LocalDateTime blockedTimestamp;
}
