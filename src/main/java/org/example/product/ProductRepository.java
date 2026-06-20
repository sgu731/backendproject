package org.example.product;

import lombok.RequiredArgsConstructor;
import org.example.product.dto.UpdateProductRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(Product product) {

        String sql = """
                insert into products
                (
                    name,
                    price,
                    stock
                )
                values
                (
                    ?,?,?
                )
                """;

        jdbcTemplate.update(
                sql,
                product.name(),
                product.price(),
                product.stock()
        );
    }

    public void update(
            Long id,
            UpdateProductRequest request) {

        String sql = """
            update products
            set
                name = ?,
                price = ?,
                stock = ?,
                updated_at = now()
            where id = ?
            and deleted = false
            """;

        jdbcTemplate.update(
                sql,
                request.name(),
                request.price(),
                request.stock(),
                id);
    }

    public void delete(Long id) {

        String sql = """
            update products
            set deleted = true
            where id = ?
            """;

        jdbcTemplate.update(sql, id);

    }

    public List<Product> findAll(
            int page,
            int size) {

        String sql = """
            select
                id,
                name,
                price,
                stock,
                deleted
            from products
            where deleted = false
            order by id
            limit ?
            offset ?
            """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Product(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getBigDecimal("price"),
                                rs.getInt("stock"),
                                rs.getBoolean("deleted")
                        ),
                size,
                page * size
        );
    }

    public Long count() {

        String sql = """
            select count(*)
            from products
            where deleted = false
            """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class
        );
    }

    public Optional<Product> findById(Long id) {

        String sql = """
            select
                id,
                name,
                price,
                stock,
                deleted
            from products
            where id = ?
            and deleted = false
            """;

        List<Product> products =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) ->
                                new Product(
                                        rs.getLong("id"),
                                        rs.getString("name"),
                                        rs.getBigDecimal("price"),
                                        rs.getInt("stock"),
                                        rs.getBoolean("deleted")
                                ),
                        id);

        return products.stream().findFirst();
    }
}