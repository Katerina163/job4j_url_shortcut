package ru.job4j.url.shortcut.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.job4j.url.shortcut.dto.request.LoginDto;
import ru.job4j.url.shortcut.dto.request.SiteDto;
import ru.job4j.url.shortcut.dto.response.RegistrationDto;
import ru.job4j.url.shortcut.dto.response.StatisticDto;
import ru.job4j.url.shortcut.mapper.Mapper;
import ru.job4j.url.shortcut.mapper.WebsiteRegistrationDtoMapper;
import ru.job4j.url.shortcut.model.Page;
import ru.job4j.url.shortcut.model.Website;
import ru.job4j.url.shortcut.repository.WebsiteRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SimpleWebsiteServiceTest {
    private final WebsiteRepository websiteRepository = Mockito.mock(WebsiteRepository.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final Mapper<Page, StatisticDto> pageMapper = Mockito.mock(Mapper.class);
    private final Mapper<Website, RegistrationDto> webMapper = new WebsiteRegistrationDtoMapper();
    private final SimpleWebsiteService service = new SimpleWebsiteService(websiteRepository, encoder, pageMapper, webMapper);

    @Test
    public void whenRegistrationAndFindByDomain() {
        var domain = "github.com";
        var login = "login";
        var password = "password";
        var site = new SiteDto(domain);
        var website = new Website();
        website.setLogin(login);
        website.setPassword(password);
        when(websiteRepository.findByDomain(domain))
                .thenReturn(Optional.of(website));

        var result = service.registration(site);

        assertTrue(result.isPresent());
        assertFalse(result.get().isRegistration());
        assertThat(result.get().getLogin(), is(login));
        assertThat(result.get().getPassword(), is(password));
    }

    @Test
    public void whenNewAndRegistration() {
        var domain = "github.com";
        var login = "login";
        var password = "password";
        var site = new SiteDto(domain);
        var website = new Website();
        website.setLogin(login);
        website.setPassword(password);
        when(websiteRepository.findByDomain(domain))
                .thenReturn(Optional.empty());
        ArgumentCaptor<Website> websiteAC = ArgumentCaptor.forClass(Website.class);
        when(websiteRepository.save(websiteAC.capture())).thenReturn(website);

        var result = service.registration(site);

        assertTrue(result.isPresent());
        assertTrue(result.get().isRegistration());
        assertThat(result.get().getLogin(), is(login));
        var webCapture = websiteAC.getValue();
        assertThat(webCapture.getLogin().length(), is(36));
        assertThat(webCapture.getPassword().length(), is(60));
        assertThat(webCapture.getDomain(), is(domain));
    }

    @Test
    public void whenGetStatistic() {
        var login = "login";
        var page = new Page();
        Collection<Page> pages = List.of(page);
        when(websiteRepository.findByWebsiteLogin(login))
                .thenReturn(pages);
        var dto = new StatisticDto(page.getPath(), page.getCount());
        when(pageMapper.convert(page))
                .thenReturn(dto);

        var result = service.getStatistic(login);

        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(dto));
    }

    @Test
    public void whenFind() {
        var login = "login";
        var dto = new LoginDto();
        dto.setLogin(login);
        when(websiteRepository.findByLogin(login))
                .thenReturn(Optional.of(new Website()));

        var result = service.find(dto);

        assertTrue(result);
    }

    @Test
    public void whenNotFind() {
        var login = "login";
        var dto = new LoginDto();
        dto.setLogin(login);
        when(websiteRepository.findByLogin(login))
                .thenReturn(Optional.empty());

        var result = service.find(dto);

        assertFalse(result);
    }

    @Test
    public void whenLoadUserByUsername() {
        var login = "login";
        var password = "password";
        var website = new Website();
        website.setLogin(login);
        website.setPassword(password);
        when(websiteRepository.findByLogin(login))
                .thenReturn(Optional.of(website));

        var result = service.loadUserByUsername(login);

        assertThat(result.getUsername(), is(login));
        assertThat(result.getPassword(), is(password));
        assertTrue(result.getAuthorities().isEmpty());
    }

    @Test
    public void whenLoadUserByUsernameThrowsException() {
        var login = "login";
        when(websiteRepository.findByLogin(login))
                .thenReturn(Optional.empty());

        UsernameNotFoundException exp = assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername(login)
        );

        assertThat(exp.getMessage(), is("login"));
    }
}