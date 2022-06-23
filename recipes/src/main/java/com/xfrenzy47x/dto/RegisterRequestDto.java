package com.xfrenzy47x.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @NotBlank
    @Email
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;
}
