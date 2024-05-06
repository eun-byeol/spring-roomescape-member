package roomescape.core.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.core.domain.ReservationTime;
import roomescape.core.dto.BookingTimeResponseDto;
import roomescape.core.dto.ReservationTimeRequestDto;
import roomescape.core.dto.ReservationTimeResponseDto;
import roomescape.core.repository.ReservationRepository;
import roomescape.core.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository,
                                  final ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public ReservationTimeResponseDto create(final ReservationTimeRequestDto request) {
        final ReservationTime reservationTime = new ReservationTime(request.getStartAt());
        validateDuplicatedStartAt(reservationTime);
        final Long id = reservationTimeRepository.save(reservationTime);
        return new ReservationTimeResponseDto(id, reservationTime);
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponseDto> findAll() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingTimeResponseDto> findBookable(final String date, final long themeId) {
        return reservationRepository.findAllByDateNotOrThemeIdNot(date, themeId);
    }

    @Transactional
    public void delete(final long id) {
        final boolean exist = reservationRepository.existByTimeId(id);
        if (exist) {
            throw new IllegalArgumentException("해당 시간에 예약한 내역이 존재하여 삭제할 수 없습니다.");
        }
        reservationTimeRepository.deleteById(id);
    }

    private void validateDuplicatedStartAt(final ReservationTime reservationTime) {
        final Integer reservationTimeCount = reservationTimeRepository.countByStartAt(
                reservationTime.getStartAtString());
        if (reservationTimeCount > 0) {
            throw new IllegalArgumentException("해당 시간이 이미 존재합니다.");
        }
    }
}
