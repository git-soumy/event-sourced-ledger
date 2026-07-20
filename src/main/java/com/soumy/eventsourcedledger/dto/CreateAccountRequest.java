package com.soumy.eventsourcedledger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {

    @NotBlank(message = "Owner name is required")
    private String ownerName;
}