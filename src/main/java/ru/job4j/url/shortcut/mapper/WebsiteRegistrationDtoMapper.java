package ru.job4j.url.shortcut.mapper;

import org.springframework.stereotype.Component;
import ru.job4j.url.shortcut.dto.response.RegistrationDto;
import ru.job4j.url.shortcut.model.Website;

@Component
public class WebsiteRegistrationDtoMapper implements Mapper<Website, RegistrationDto> {
    @Override
    public RegistrationDto convert(Website website) {
        return new RegistrationDto(
                false, website.getLogin(), website.getPassword());
    }
}
