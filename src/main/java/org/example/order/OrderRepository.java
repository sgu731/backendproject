package org.example.order;

import lombok.RequiredArgsConstructor;
import org.example.order.dto.OrderResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(
            Long userId,
            Long productId,
            Integer quantity) {

        String sql = """
                insert into orders
                (
                    user_id,
                    product_id,
                    quantity
                )
                values
                (
                    ?, ?, ?
                )
                """;

        jdbcTemplate.update(
                sql,
                userId,
                productId,
                quantity
        );
    }

    public List<OrderResponse> findByUserId(
            Long userId) {

        String sql = """
                select
                    o.id,
                    o.product_id,
                    p.name,
                    o.quantity,
                    o.created_at
                from orders o
                join products p
                    on p.id = o.product_id
                where o.user_id = ?
                order by o.created_at desc
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new OrderResponse(
                                rs.getLong("id"),
                                rs.getLong("product_id"),
                                rs.getString("name"),
                                rs.getInt("quantity"),
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        ),
                userId
        );
    }

}