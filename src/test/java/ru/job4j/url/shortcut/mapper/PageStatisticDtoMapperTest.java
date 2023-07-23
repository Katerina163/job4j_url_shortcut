package ru.job4j.url.shortcut.mapper;

import org.junit.jupiter.api.Test;
import ru.job4j.url.shortcut.model.Page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PageStatisticDtoMapperTest {
    private final PageStatisticDtoMapper mapper = new PageStatisticDtoMapper();

    @Test
    public void convert() {
        var page = new Page();
        page.setPath("https://github.com/Katerina163");
        page.setCount(3);
        var result = mapper.convert(page);
        assertThat(result.url(), is(page.getPath()));
        assertThat(result.total(), is(page.getCount()));
    }
}