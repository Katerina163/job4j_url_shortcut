package ru.job4j.url.shortcut.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NotNull
@Data
public class ConvertUrlDto {
    @Pattern(regexp = ".*://.*/.*")
    private String url;
}
