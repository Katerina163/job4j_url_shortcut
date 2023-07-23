package ru.job4j.url.shortcut.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.dto.request.LoginDto;
import ru.job4j.url.shortcut.dto.request.SiteDto;
import ru.job4j.url.shortcut.dto.response.RegistrationDto;
import ru.job4j.url.shortcut.dto.response.StatisticDto;
import ru.job4j.url.shortcut.mapper.Mapper;
import ru.job4j.url.shortcut.model.Page;
import ru.job4j.url.shortcut.model.Website;
import ru.job4j.url.shortcut.repository.WebsiteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Service
@Transactional
public class SimpleWebsiteService implements WebsiteService, UserDetailsService {
    private final WebsiteRepository websiteRepository;
    private final BCryptPasswordEncoder encoder;
    private final Mapper<Page, StatisticDto> pageMapper;
    private final Mapper<Website, RegistrationDto> webMapper;

    public SimpleWebsiteService(WebsiteRepository websiteRepository,
                                @Lazy BCryptPasswordEncoder encoder,
                                Mapper<Page, StatisticDto> pageStatisticDtoMapper,
                                Mapper<Website, RegistrationDto> websiteRegistrationDtoMapper) {
        this.websiteRepository = websiteRepository;
        this.encoder = encoder;
        pageMapper = pageStatisticDtoMapper;
        webMapper = websiteRegistrationDtoMapper;
    }

    @Override
    public Optional<RegistrationDto> registration(SiteDto site) {
        var optional = websiteRepository.findByDomain(site.site());
        if (optional.isPresent()) {
            return optional.map(webMapper::convert);
        }
        var website = new Website();
        website.setLogin(UUID.randomUUID().toString());
        var password = UUID.randomUUID().toString();
        website.setPassword(encoder.encode(password));
        website.setDomain(site.site());
        var result = Optional.of(websiteRepository.save(website)).map(webMapper::convert);
        result.get().setRegistration(true);
        result.get().setPassword(password);
        return result;
    }

    @Override
    public List<StatisticDto> getStatistic(String login) {
        return websiteRepository.findByWebsiteLogin(login).stream().map(pageMapper::convert).toList();
    }

    @Override
    public boolean find(LoginDto dto) {
        return websiteRepository.findByLogin(dto.getLogin()).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = websiteRepository.findByLogin(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.get().getLogin(), user.get().getPassword(), emptyList());
    }
}
