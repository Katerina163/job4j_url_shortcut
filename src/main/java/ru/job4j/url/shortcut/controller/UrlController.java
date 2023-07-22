package ru.job4j.url.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.shortcut.dto.request.ConvertUrlDto;
import ru.job4j.url.shortcut.dto.response.CodeDto;
import ru.job4j.url.shortcut.dto.response.StatisticDto;
import ru.job4j.url.shortcut.service.PageService;
import ru.job4j.url.shortcut.service.WebsiteService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class UrlController {
    private final WebsiteService websiteService;
    private final PageService pageService;

    public UrlController(WebsiteService simpleWebsiteService,
                         PageService simplePageService) {
        websiteService = simpleWebsiteService;
        pageService = simplePageService;
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
