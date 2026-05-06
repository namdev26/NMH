-- ============================================================
-- BƯỚC 1: Thêm cột SupplierId vào bảng tblCostume
-- Nếu Hibernate đã tự tạo cột rồi thì BỎ QUA bước này,
-- chạy thẳng từ BƯỚC 2
-- ============================================================
ALTER TABLE tblCostume
    ADD COLUMN SupplierId BIGINT NULL;

-- ============================================================
-- BƯỚC 2: Phân bổ nhà cung cấp cho trang phục (round-robin)
-- ============================================================
UPDATE tblCostume c
INNER JOIN (
    SELECT
        c2.id AS costume_id,
        (
            SELECT s.id
            FROM tblSupplier s
            ORDER BY s.id
            LIMIT 1 OFFSET ((c2.rn - 1) % (SELECT COUNT(*) FROM tblSupplier))
        ) AS supplier_id
    FROM (
        SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS rn
        FROM tblCostume
    ) c2
) assignment ON c.id = assignment.costume_id
SET c.SupplierId = assignment.supplier_id;

-- ============================================================
-- BƯỚC 3: Kiểm tra kết quả
-- ============================================================
SELECT
    c.id,
    c.Name,
    c.SupplierId,
    s.Name AS supplier_name
FROM tblCostume c
LEFT JOIN tblSupplier s ON c.SupplierId = s.id
ORDER BY c.id;
