package ru.job4j.url.shortcut.service;

import ru.job4j.url.shortcut.dto.CodeDto;
import ru.job4j.url.shortcut.dto.ConvertUrlDto;

import java.util.Optional;

public interface PageService {
    Optional<CodeDto> getCode(ConvertUrlDto convert);

    String findPath(String code);
}
