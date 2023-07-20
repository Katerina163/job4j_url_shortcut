package ru.job4j.url.shortcut.service;

import ru.job4j.url.shortcut.dto.response.CodeDto;
import ru.job4j.url.shortcut.dto.request.ConvertUrlDto;

import java.util.Optional;

public interface PageService {
    Optional<CodeDto> getCode(ConvertUrlDto convert);

    String findPath(String code);
}
