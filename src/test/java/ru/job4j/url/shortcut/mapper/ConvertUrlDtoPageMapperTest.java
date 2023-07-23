package ru.job4j.url.shortcut.mapper;

import org.junit.jupiter.api.Test;
import ru.job4j.url.shortcut.dto.request.ConvertUrlDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ConvertUrlDtoPageMapperTest {
    private final ConvertUrlDtoPageMapper mapper = new ConvertUrlDtoPageMapper();

    @Test
    public void convert() {
        var dto = new ConvertUrlDto();
        dto.setUrl("https://github.com/Katerina163");
        var result = mapper.convert(dto);
        assertThat(result.getCode().length(), is(10));
        assertThat(result.getPath(), is(dto.getUrl()));
    }
}