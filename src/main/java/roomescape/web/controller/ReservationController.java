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
import org.springframework.web.bind.annotation.RestController;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;
import roomescape.core.dto.ReservationRequestDto;
import roomescape.core.dto.ReservationResponseDto;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;
import roomescape.core.service.ThemeService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@RequestBody @Valid final ReservationRequestDto request) {
        final Reservation reservation = reservationService.create(request);
        final ReservationResponseDto response = new ReservationResponseDto(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAll() {
        return reservationService.findAll()
                .stream()
                .map(ReservationResponseDto::new)
                .collect(collectingAndThen(toList(), ResponseEntity::ok));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
