# 系統設計文件

## 1. 系統架構

本專案採用分層架構：

Controller\
↓\
Service\
↓\
Repository\
↓\
PostgreSQL

主要模組包含：

-   auth
-   product
-   order
-   audit
-   exception
-   ratelimit

選擇 JdbcTemplate 而非 JPA，目的是取得更完整的 SQL 控制能力，避免 ORM
帶來不必要的抽象與效能成本。

------------------------------------------------------------------------

## 2. 資料庫設計

主要資料表：

-   users
-   products
-   orders
-   audit_logs

關聯：

users (1) ---- (N) orders (N) ---- (1) products

商品採用 Soft Delete，以保留歷史訂單資料並避免關聯問題。

### Index

建立以下索引：

-   products(price)
-   products(deleted)
-   products USING gin(name gin_trgm_ops)

目的：

-   提升價格篩選效率
-   加速排序
-   支援模糊搜尋

------------------------------------------------------------------------

## 3. 庫存併發控制

扣庫存使用 Atomic Update：

UPDATE products SET stock = stock - ? WHERE id = ? AND stock \>= ?

只有當 affected rows = 1 時才建立訂單。

此方式可保證庫存不會變成負數，並避免悲觀鎖造成的鎖競爭。

------------------------------------------------------------------------

## 4. 商品搜尋

支援：

-   關鍵字模糊搜尋
-   價格區間篩選
-   排序
-   分頁

利用 PostgreSQL 的 pg_trgm extension 與 GIN index 提升模糊搜尋效能。

------------------------------------------------------------------------

## 5. API 限流

使用 Bucket4j 實作 Token Bucket 演算法。

目前策略：

-   每個 IP 每分鐘最多 100 次請求

未來可將 bucket 狀態移至 Redis，以實現分散式限流。

------------------------------------------------------------------------

## 6. Trade-off

### Soft Delete

保留歷史訂單資料，避免刪除商品後造成資料不一致。

### JdbcTemplate vs JPA

選擇 JdbcTemplate 的原因：

-   完整控制 SQL
-   減少 ORM abstraction
-   容易進行效能調校

### Atomic Update vs Pessimistic Lock

Atomic Update 可避免鎖競爭，並提供較好的吞吐量。

------------------------------------------------------------------------

## 7. 系統擴充策略

目前：

PostgreSQL + pg_trgm

適用於約十萬到百萬筆商品。

### 下一階段

改用 Keyset Pagination：

WHERE id \> ? LIMIT 20

避免 OFFSET 深分頁造成效能下降。

### 大規模搜尋

PostgreSQL ↓ Debezium CDC ↓ Kafka ↓ Elasticsearch

由 Elasticsearch 負責：

-   模糊搜尋
-   Auto Complete
-   Relevance Ranking

而 PostgreSQL 作為 Source of Truth。

------------------------------------------------------------------------

## 8. AI 使用聲明

開發過程中有使用 AI 工具進行：

-   腦力激盪
-   程式碼 Review
-   架構討論

所有產出的程式碼與設計皆經過人工驗證與測試。

包含資料庫設計、併發控制策略與索引設計，皆透過實際測試與官方文件確認其正確性。