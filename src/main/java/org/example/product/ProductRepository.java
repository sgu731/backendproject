package org.example.product;

import lombok.RequiredArgsConstructor;
import org.example.product.dto.ProductSearchRequest;
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

    public List<Product> search(ProductSearchRequest request) {

        StringBuilder sql = new StringBuilder("""
        select
            id,
            name,
            price,
            stock,
            deleted
        from products
        where deleted = false
        """);

        List<Object> params = new ArrayList<>();

        if (request.keyword() != null
                && !request.keyword().isBlank()) {

            sql.append(" and name ilike ? ");

            params.add("%" + request.keyword() + "%");
        }

        if (request.minPrice() != null) {

            sql.append(" and price >= ? ");

            params.add(request.minPrice());
        }

        if (request.maxPrice() != null) {

            sql.append(" and price <= ? ");

            params.add(request.maxPrice());
        }

        String sortBy = switch (request.sortBy()) {

            case "name" -> "name";

            case "price" -> "price";

            default -> "id";
        };

        String direction =
                "desc".equalsIgnoreCase(request.direction())
                        ? "desc"
                        : "asc";

        sql.append(" order by ")
                .append(sortBy)
                .append(" ")
                .append(direction);

        sql.append(" limit ? offset ? ");

        params.add(request.size());
        params.add(request.page() * request.size());

        return jdbcTemplate.query(

                sql.toString(),

                (rs, rowNum) ->
                        new Product(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getBigDecimal("price"),
                                rs.getInt("stock"),
                                rs.getBoolean("deleted")
                        ),

                params.toArray()
        );
    }

    public Long count(ProductSearchRequest request) {

        StringBuilder sql = new StringBuilder("""
        select count(*)
        from products
        where deleted = false
        """);

        List<Object> params = new ArrayList<>();

        if (request.keyword() != null && !request.keyword().isBlank()) {

            sql.append(" and name ilike ? ");

            params.add("%" + request.keyword() + "%");
        }

        if (request.minPrice() != null) {

            sql.append(" and price >= ? ");

            params.add(request.minPrice());
        }

        if (request.maxPrice() != null) {

            sql.append(" and price <= ? ");

            params.add(request.maxPrice());
        }

        return jdbcTemplate.queryForObject(
                sql.toString(),
                Long.class,
                params.toArray());
    }

    public boolean decreaseStock(
            Long productId,
            Integer quantity) {

        String sql = """
            UPDATE products
            SET stock = stock - ?
            WHERE id = ?
              AND deleted = false
              AND stock >= ?
            """;

        int affectedRows =
                jdbcTemplate.update(
                        sql,
                        quantity,
                        productId,
                        quantity);

        return affectedRows == 1;
    }
}