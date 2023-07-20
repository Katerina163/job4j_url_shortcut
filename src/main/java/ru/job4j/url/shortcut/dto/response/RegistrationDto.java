package ru.job4j.url.shortcut.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationDto {
    private boolean registration;
    private String login;
    private String password;
}
