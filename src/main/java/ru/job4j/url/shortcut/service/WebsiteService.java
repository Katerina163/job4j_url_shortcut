package ru.job4j.url.shortcut.service;

import ru.job4j.url.shortcut.dto.LoginDto;
import ru.job4j.url.shortcut.dto.RegistrationDto;
import ru.job4j.url.shortcut.dto.SiteDto;
import ru.job4j.url.shortcut.dto.StatisticDto;

import java.util.List;
import java.util.Optional;

public interface WebsiteService {
    Optional<RegistrationDto> registration(SiteDto site);

    List<StatisticDto> getStatistic(String login);

    boolean find(LoginDto dto);
}
