# Design Document

## 1. Architecture

The application follows a layered architecture:

Controller\
↓\
Service\
↓\
Repository\
↓\
PostgreSQL

Main modules:

-   auth
-   product
-   order
-   audit
-   exception
-   ratelimit

JdbcTemplate is used instead of JPA to provide full control over SQL and
avoid unnecessary ORM abstraction.

------------------------------------------------------------------------

## 2. Database Design

Tables:

-   users
-   products
-   orders
-   audit_logs

Relationship:

users (1) ---- (N) orders (N) ---- (1) products

Soft delete is used for products to preserve historical order records.

### Indexes

-   products(price)
-   products(deleted)
-   products USING gin(name gin_trgm_ops)

These indexes improve filtering, sorting and fuzzy search performance.

------------------------------------------------------------------------

## 3. Inventory Concurrency Control

Inventory deduction uses atomic SQL:

UPDATE products SET stock = stock - ? WHERE id = ? AND stock \>= ?

Orders are created only when affected rows = 1.

This prevents stock from becoming negative without explicit locking.

------------------------------------------------------------------------

## 4. Product Search

Supports:

-   keyword search
-   price filtering
-   sorting
-   pagination

PostgreSQL pg_trgm extension with GIN index is used to accelerate fuzzy
search.

------------------------------------------------------------------------

## 5. Rate Limiting

Bucket4j is used with Token Bucket Algorithm.

Current policy:

-   100 requests per minute per IP

Future improvement:

-   Redis-based distributed rate limiting

------------------------------------------------------------------------

## 6. Trade-offs

### Soft Delete

Preserves historical order records and avoids referential integrity
problems.

### JdbcTemplate vs JPA

JdbcTemplate was chosen because:

-   full SQL control
-   lower abstraction overhead
-   simpler performance tuning

### Atomic Update vs Pessimistic Lock

Atomic update avoids lock contention and provides higher throughput.

------------------------------------------------------------------------

## 7. Scaling Strategy

Current:

PostgreSQL + pg_trgm

Suitable for approximately 100K \~ 1M products.

### Next Stage

Use Keyset Pagination:

WHERE id \> ? LIMIT 20

to avoid deep OFFSET pagination.

### Large Scale

PostgreSQL ↓ Debezium CDC ↓ Kafka ↓ Elasticsearch

Elasticsearch handles fuzzy search, autocomplete and relevance scoring,
while PostgreSQL remains the source of truth.

------------------------------------------------------------------------

## 8. AI Usage Statement

AI tools were used for brainstorming, code review and design discussion.

All generated code and architectural decisions were manually reviewed
and tested before adoption.

Important decisions such as schema design, concurrency control and
indexing strategy were validated through experimentation and
documentation review.