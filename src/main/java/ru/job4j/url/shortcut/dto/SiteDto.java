package ru.job4j.url.shortcut.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NotNull
public record SiteDto(@NotBlank
                      String site) {
}
