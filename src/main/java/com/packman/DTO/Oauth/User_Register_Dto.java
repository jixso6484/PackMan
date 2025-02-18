package com.packman.DTO.Oauth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User_Register_Dto{

    @NotBlank(message = "provider는 error")
    private String provider;

    @NotBlank(message = "providerId는 error")
    private String providerId;

    @NotBlank(message = "email은 error")
    @Email(message = "email은 유효한 형식이 아님")
    private String email;

    @NotBlank(message = "name는 error")
    private String name;

    @NotBlank(message = "nickname는 error")
    private String nickname;

    private String profileImage;
    
    private String birthday;
    @NotBlank(message = "gender는 필수 항목입니다.")
    private String gender;
    
    private String ageRange;    
}