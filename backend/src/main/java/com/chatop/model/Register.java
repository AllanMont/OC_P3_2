package com.chatop.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class Register {
    private String name;
    private String email;
    private String password;
}
