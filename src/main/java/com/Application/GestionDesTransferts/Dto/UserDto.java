package com.Application.GestionDesTransferts.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserDto {

    private String email;
    private String password;
    private String role;
    private String fullname;

    public UserDto(String email, String password, String role, String fullname) {
        super();
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }

    public UserDto() {
        super();
    }

}
