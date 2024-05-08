package roomescape.web.controller;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import roomescape.core.domain.ReservationTime;
import roomescape.core.dto.ReservationTimeWithStateDto;
import roomescape.core.dto.ReservationTimeRequestDto;
import roomescape.core.dto.ReservationTimeResponseDto;
import roomescape.core.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> create(
            @RequestBody @Valid final ReservationTimeRequestDto request
    ) {
        final ReservationTime time = reservationTimeService.create(request);
        final ReservationTimeResponseDto response = new ReservationTimeResponseDto(time);
        return ResponseEntity.created(URI.create("/times/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> findAll() {
        return reservationTimeService.findAll()
                .stream()
                .map(ReservationTimeResponseDto::new)
                .collect(collectingAndThen(toList(), ResponseEntity::ok));
    }

    @GetMapping(params = {"date", "themeId"})
    public ResponseEntity<List<ReservationTimeWithStateDto>> findBookable(
            @RequestParam("date") String date,
            @RequestParam("themeId") Long themeId
    ) {
        return ResponseEntity.ok(reservationTimeService.findBookable(date, themeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        reservationTimeService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
