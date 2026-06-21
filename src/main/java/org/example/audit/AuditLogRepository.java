package org.example.audit;

import lombok.RequiredArgsConstructor;
import org.example.audit.dto.AuditLogResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuditLogRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(
            String actor,
            String action,
            String beforeValue,
            String afterValue) {

        String sql = """
                insert into audit_logs
                (
                    actor,
                    action,
                    before_value,
                    after_value
                )
                values
                (
                    ?,?,?,?
                )
                """;

        jdbcTemplate.update(
                sql,
                actor,
                action,
                beforeValue,
                afterValue);
    }

    public List<AuditLogResponse> findAll() {

        String sql = """
                select
                    id,
                    actor,
                    action,
                    before_value,
                    after_value,
                    created_at
                from audit_logs
                order by created_at desc
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new AuditLogResponse(
                                rs.getLong("id"),
                                rs.getString("actor"),
                                rs.getString("action"),
                                rs.getString("before_value"),
                                rs.getString("after_value"),
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        )
        );
    }
}