package roomescape.web.repository;

import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.core.domain.Member;
import roomescape.core.repository.MemberRepository;
import roomescape.web.exception.NotFoundException;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public MemberRepositoryImpl(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final Member member) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", member.getName())
                .addValue("email", member.getEmail())
                .addValue("password", member.getPassword())
                .addValue("role", member.getRoleName());
        return jdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public boolean hasDuplicateEmail(final String email) {
        final String query = "SELECT EXISTS(SELECT 1 FROM member WHERE email = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, email));
    }

    @Override
    public Member findByEmail(final String email) {
        try {
            final String query = "SELECT id, name, email, password, role FROM member WHERE email = ?";
            return jdbcTemplate.queryForObject(query, getMemberRowMapper(), email);
        } catch (DataAccessException e) {
            throw new NotFoundException("해당 이메일 사용자를 찾을 수 없습니다.");
        }
    }

    private RowMapper<Member> getMemberRowMapper() {
        return (resultSet, rowNum) -> new Member(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("role")
        );
    }
}
