# 🗝️방탈출 예약 관리🗝️

## 기능 명세서

### 주간 인기 테마 페이지

- `/` 으로 접속할 수 있다.
- 지난 한 주 동안 가장 많이 예약된 순서로 테마를 최대 10개 보여준다.

### 관리자 메인 페이지

- `/admin` 으로 접속할 수 있다.
- 관리자 페이지를 볼 수 있다.
- 네비게이션 바의 Reservation을 누르면 관리자 예약 페이지로 이동한다.

### 예약 관리 페이지

- `/admin/reservation` 으로 접속할 수 있다.
- 예약 목록을 볼 수 있다.
    - 예약 번호, 이름, 날짜, 시간을 볼 수 있다.
- 예약을 추가할 수 있다.
    - 이름, 날짜, 시간을 입력하여 추가한다.
    - 빈 값이 있으면 예외를 던진다.
    - 같은 날짜, 시간, 테마에 예약하면 예외를 던진다.
    - 지나간 날짜와 시간에 예약하면 예외를 던진다.
- 예약을 삭제할 수 있다.

### 시간 관리 페이지

- `/admin/time` 으로 접속할 수 있다.
- 시간 목록을 볼 수 있다.
    - 순서, 시간을 볼 수 있다.
- 시간을 추가할 수 있다.
    - 시간을 입력하여 추가한다.
    - 빈 값이 있으면 예외를 던진다.
- 시간을 삭제할 수 있다.
    - 해당 시간에 예약한 내역이 존재하면 예외를 던진다.

### 테마 관리 페이지

- `/admin/theme` 으로 접속할 수 있다.
- 테마 목록을 볼 수 있다.
    - 순서, 테마 이름, 설명을 볼 수 있다.
- 테마를 추가할 수 있다.
    - 테마 이름, 설명, 섬네일을 입력하여 추가한다.
- 테마를 삭제할 수 있다.
    - 해당 테마를 예약한 내역이 존재하면 예외를 던진다.

### 사용자 예약 페이지

- `/reservation` 으로 접속할 수 있다.
- 날짜, 테마를 선택하면 예약 가능한 시간을 볼 수 있다.
- 날짜, 테마, 시간을 선택하고 이름을 입력하여 예약할 수 있다.

## API 명세서

| HTTP Method | URI                                               | Description      |
|-------------|---------------------------------------------------|------------------|
| GET         | `/`                                               | 주간 인기 테마 페이지     |
| GET         | `/admin`                                          | 관리자 메인 페이지       | 
| GET         | `/admin/reservation`                              | 예약 관리 페이지        |
| GET         | `/admin/time`                                     | 시간 관리 페이지        |
| GET         | `/admin/theme`                                    | 테마 관리 페이지        |
| GET         | `/reservation`                                    | 사용자 예약 페이지       |
| GET         | `/reservations`                                   | 예약 목록 조회         |
| POST        | `/reservations`                                   | 예약 추가            | 
| DELETE      | `/reservations/{id}`                              | 예약 삭제            |
| GET         | `/times`                                          | 시간 목록 조회         |
| GET         | `/times/available?date={date}&theme-id={themeId}` | 예약 가능 시간 목록 조회   |
| POST        | `/times`                                          | 시간 추가            | 
| DELETE      | `/times/{id}`                                     | 시간 삭제            |
| GET         | `/themes`                                         | 테마 목록 조회         |
| GET         | `/themes/popular?period-day={periodDay}`          | 기간 별 인기 테마 목록 조회 |
| POST        | `/themes`                                         | 테마 추가            |
| DELETE      | `/themes/{id}`                                    | 테마 삭제            |
