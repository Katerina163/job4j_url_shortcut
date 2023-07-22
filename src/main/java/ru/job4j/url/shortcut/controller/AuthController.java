package ru.job4j.url.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.url.shortcut.dto.request.LoginDto;
import ru.job4j.url.shortcut.dto.request.SiteDto;
import ru.job4j.url.shortcut.dto.response.RegistrationDto;
import ru.job4j.url.shortcut.service.WebsiteService;

import javax.validation.Valid;

@RestController
public class AuthController {
    private final WebsiteService websiteService;

    private final BCryptPasswordEncoder encoder;

    public AuthController(WebsiteService websiteService,
                          BCryptPasswordEncoder encoder) {
        this.websiteService = websiteService;
        this.encoder = encoder;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationDto> registration(@Valid @RequestBody SiteDto site) {
        var registr = websiteService.registration(site);
        return new ResponseEntity<>(
                registr.orElseThrow(),
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        var register = websiteService.find(dto);
        return register ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
