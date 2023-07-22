package ru.job4j.url.shortcut.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.url.shortcut.dto.request.ConvertUrlDto;
import ru.job4j.url.shortcut.model.Page;
import ru.job4j.url.shortcut.random.GeneratorRandomString;

@Component
public class ConvertUrlDtoPageMapper implements Mapper<ConvertUrlDto, Page> {
    @Override
    public Page convert(ConvertUrlDto convertUrlDto) {
        var page = new Page();
        page.setPath(convertUrlDto.getUrl());
        String code = GeneratorRandomString.generate();
        page.setCode(code);
        return page;
    }
}
