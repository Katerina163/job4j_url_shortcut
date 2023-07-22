package ru.job4j.url.shortcut.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.url.shortcut.dto.response.StatisticDto;
import ru.job4j.url.shortcut.model.Page;

@Component
public class PageStatisticDtoMapper implements Mapper<Page, StatisticDto> {
    @Override
    public StatisticDto convert(Page page) {
        return new StatisticDto(page.getPath(), page.getCount());
    }
}
