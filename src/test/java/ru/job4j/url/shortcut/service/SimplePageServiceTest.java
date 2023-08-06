package ru.job4j.url.shortcut.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.job4j.url.shortcut.dto.request.ConvertUrlDto;
import ru.job4j.url.shortcut.mapper.Mapper;
import ru.job4j.url.shortcut.model.Page;
import ru.job4j.url.shortcut.model.Website;
import ru.job4j.url.shortcut.repository.PageRepository;
import ru.job4j.url.shortcut.repository.WebsiteRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class SimplePageServiceTest {
    private final WebsiteRepository websiteRepository = Mockito.mock(WebsiteRepository.class);
    private final PageRepository pageRepository = Mockito.mock(PageRepository.class);
    private final Mapper mapper = Mockito.mock(Mapper.class);
    private final SimplePageService service = new SimplePageService(websiteRepository, pageRepository, mapper);

    @Test
    public void whenFindPath() {
        var convert = new ConvertUrlDto();
        convert.setUrl("https://github.com");
        var website = new Website();
        website.setLogin("b49a5e2c-75e2-4a1f-942c-4d0c1e25b25a");
        website.setPassword("$2a$10$T4rRFuH7RNdMsA2YTQb.quFSRqQWBfHROfwGk6n13.u84fI..H/WC");
        var page = new Page();
        page.setCode("dDKS8sh&S");
        when(websiteRepository.findByDomainWithPages("github.com"))
                .thenReturn(Optional.of(website));
        when(mapper.convert(convert))
                .thenReturn(page);

        var result = service.getCode(convert);

        assertTrue(result.isPresent());
        assertThat(result.get().code(), is(page.getCode()));
    }

    @Test
    public void whenGetCode() {
        var str = "https://github.com/Katerina163";
        var page = new Page();
        page.setId(1);
        var code = "dDKS8sh&S";
        page.setPath(str);
        page.setCount(1);
        when(pageRepository.findByCode(code)).thenReturn(page);

        var result = service.findPath(code);

        assertThat(result, is(str));
    }

    @Test
    public void whenGetCodeWithIncorrectDomain() {
        var convert = new ConvertUrlDto();
        convert.setUrl("https://github.com");
        when(websiteRepository.findByDomainWithPages("github.com"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exp = assertThrows(
                IllegalArgumentException.class,
                () -> service.getCode(convert)
        );

        assertThat(exp.getMessage(), is("Некорректный домен"));
    }
}