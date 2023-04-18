package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 20, message = "Surname should be from 1 to 20 symbols")
    private String name;
    @Size(min = 2, max = 20, message = "Surname should be from 1 to 20 symbols")
    private String surname;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String repeatPassword;
    private String captcha;
    @NotBlank
    private String repeatСaptcha;
    private MultipartFile image;
}
