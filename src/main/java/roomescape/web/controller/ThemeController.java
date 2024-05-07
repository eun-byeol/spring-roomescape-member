package roomescape.web.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.core.domain.Theme;
import roomescape.core.dto.ThemeRequestDto;
import roomescape.core.dto.ThemeResponseDto;
import roomescape.core.service.ThemeService;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(final ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<ThemeResponseDto> create(@RequestBody @Valid final ThemeRequestDto request) {
        final Theme theme = new Theme(request.getName(), request.getDescription(), request.getThumbnail());
        final Theme savedTheme = themeService.create(theme);
        final ThemeResponseDto response = new ThemeResponseDto(savedTheme);
        return ResponseEntity.created(URI.create("/themes/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDto>> findAll() {
        final List<Theme> themes = themeService.findAll();
        final List<ThemeResponseDto> response = themes.stream()
                .map(ThemeResponseDto::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<ThemeResponseDto>> findPopular() {
        final List<Theme> themes = themeService.findPopular();
        final List<ThemeResponseDto> response = themes.stream()
                .map(ThemeResponseDto::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        themeService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
