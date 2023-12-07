package ru.practicum.api.user;

import lombok.Data;
import ru.practicum.service.user.validation.EmailPartsLengthValidation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewUserRequest {
    @Size(min = 6, max = 254)
    @Email
    @NotBlank
    @EmailPartsLengthValidation
    private String email;
    @Size(min = 2, max = 250, message = "Name должно быть не короче 2х и не длиннее 250 символов")
    @NotBlank
    private String name;
}
