package ru.job4j.url.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.shortcut.dto.LoginDto;
import ru.job4j.url.shortcut.dto.RegistrationDto;
import ru.job4j.url.shortcut.dto.SiteDto;
import ru.job4j.url.shortcut.dto.StatisticDto;
import ru.job4j.url.shortcut.model.Website;
import ru.job4j.url.shortcut.repository.WebsiteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
@Transactional
public class SimpleWebsiteService implements WebsiteService, UserDetailsService {
    private WebsiteRepository websiteRepository;
    private BCryptPasswordEncoder encoder;

    @Override
    public Optional<RegistrationDto> registration(SiteDto site) {
        var optional = websiteRepository.findByDomain(site.site());
        if (optional.isPresent()) {
            return Optional.of(new RegistrationDto(
                    false, optional.get().getLogin(), optional.get().getPassword()));
        }
        var website = new Website();
        website.setLogin(UUID.randomUUID().toString());
        var password = UUID.randomUUID().toString();
        website.setPassword(encoder.encode(password));
        website.setDomain(site.site());
        var result = websiteRepository.save(website);
        return Optional.of(new RegistrationDto(true, result.getLogin(), password));
    }

    @Override
    public List<StatisticDto> getStatistic(String login) {
        return websiteRepository.findByWebsiteLogin(login)
                .stream().map(page -> new StatisticDto(page.getPath(), page.getCount())).toList();
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