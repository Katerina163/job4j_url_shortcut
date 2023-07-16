package ru.job4j.url.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.dto.CodeDto;
import ru.job4j.url.shortcut.dto.ConvertUrlDto;
import ru.job4j.url.shortcut.model.Page;
import ru.job4j.url.shortcut.random.GeneratorRandomString;
import ru.job4j.url.shortcut.repository.PageRepository;
import ru.job4j.url.shortcut.repository.WebsiteRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class SimplePageService implements PageService {
    private WebsiteRepository websiteRepository;
    private PageRepository pageRepository;

    @Override
    public Optional<CodeDto> getCode(ConvertUrlDto convert) {
        String[] arr = convert.getUrl().split("/");
        var site = websiteRepository.findByDomainWithPages(arr[2]).orElseThrow(
                () -> new IllegalArgumentException("Некорректный домен"));
        var page = new Page();
        page.setPath(convert.getUrl());
        String code = GeneratorRandomString.generate();
        page.setCode(code);
        site.addPage(page);
        return Optional.of(new CodeDto(code));
    }

    @Override
    public String findPath(String code) {
        return pageRepository.findPathByCode(code);
    }
}
