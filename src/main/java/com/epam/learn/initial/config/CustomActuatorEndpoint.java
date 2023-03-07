package com.epam.learn.initial.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom-health")
public class CustomActuatorEndpoint {
    @ReadOperation
    public String customEndpoint() {
        return "Everything looks good";
    }
}
