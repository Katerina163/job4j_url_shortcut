package ru.job4j.url.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.shortcut.dto.*;
import ru.job4j.url.shortcut.service.PageService;
import ru.job4j.url.shortcut.service.WebsiteService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class PageController {
    private final WebsiteService websiteService;
    private final PageService pageService;
    private final BCryptPasswordEncoder encoder;

    public PageController(WebsiteService simpleWebsiteService,
                          PageService simplePageService,
                          BCryptPasswordEncoder encoder) {
        websiteService = simpleWebsiteService;
        pageService = simplePageService;
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

    @PostMapping("/convert")
    public ResponseEntity<CodeDto> convert(@Valid @RequestBody ConvertUrlDto convert) {
        var code = pageService.getCode(convert);
        return new ResponseEntity<>(
                code.orElseThrow(),
                HttpStatus.OK
        );
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> getRedirect(@NotBlank
                                            @Size(min = 10, max = 10)
                                            @PathVariable
                                            String code) {
        var url = pageService.findPath(code);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticDto>> getStatistic(Principal principal) {
        var result = websiteService.getStatistic(principal.getName());
        return new ResponseEntity<>(
                result.isEmpty() ? List.of(new StatisticDto("", -1)) : result,
                HttpStatus.OK
        );
    }
}
