package ru.job4j.url.shortcut.mapper;

import org.junit.jupiter.api.Test;
import ru.job4j.url.shortcut.model.Website;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;

class WebsiteRegistrationDtoMapperTest {
    private final WebsiteRegistrationDtoMapper mapper = new WebsiteRegistrationDtoMapper();

    @Test
    public void convert() {
        var website = new Website();
        website.setLogin("b49a5e2c-75e2-4a1f-942c-4d0c1e25b25a");
        website.setPassword("$2a$10$T4rRFuH7RNdMsA2YTQb.quFSRqQWBfHROfwGk6n13.u84fI..H/WC");
        var result = mapper.convert(website);
        assertFalse(result.isRegistration());
        assertThat(result.getLogin(), is(website.getLogin()));
        assertThat(result.getPassword(), is(website.getPassword()));
    }

}