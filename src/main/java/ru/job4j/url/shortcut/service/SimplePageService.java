package ru.job4j.url.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.dto.request.ConvertUrlDto;
import ru.job4j.url.shortcut.dto.response.CodeDto;
import ru.job4j.url.shortcut.mapper.Mapper;
import ru.job4j.url.shortcut.model.Page;
import ru.job4j.url.shortcut.repository.PageRepository;
import ru.job4j.url.shortcut.repository.WebsiteRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePageService implements PageService {
    private WebsiteRepository websiteRepository;
    private PageRepository pageRepository;
    private Mapper<ConvertUrlDto, Page> mapper;

    @Transactional
    @Override
    public Optional<CodeDto> getCode(ConvertUrlDto convert) {
        String[] arr = convert.getUrl().split("/");
        var site = websiteRepository.findByDomainWithPages(arr[2]).orElseThrow(
                () -> new IllegalArgumentException("Некорректный домен"));
        var page = Optional.of(convert).map(mapper::convert);
        site.addPage(page.get());
        return Optional.of(new CodeDto(page.get().getCode()));
    }

    @Transactional
    @Retryable
    @Override
    public String findPath(String code) {
        var result = pageRepository.findByCode(code);
        return result.getPath();
    }
}
