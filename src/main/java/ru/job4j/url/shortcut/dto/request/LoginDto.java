package ru.job4j.url.shortcut.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
public class LoginDto {
    @NotBlank
    @Size(min = 10)
    private String login;

    @NotBlank
    @Size(min = 10)
    private String password;
}
