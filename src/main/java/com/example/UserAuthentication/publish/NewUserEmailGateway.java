package com.example.UserAuthentication.publish;

import com.example.UserAuthentication.models.dto.NewUserMessage;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "newUsersMessageChannel")
public interface NewUserEmailGateway {
    void inform(NewUserMessage email);
}
