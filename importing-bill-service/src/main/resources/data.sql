-- =============================================
-- importing-bill-service seed data
-- =============================================
-- managerId và supplierId tham chiếu user_db / supplier_db

INSERT INTO tblImportingBill (Id, ManagerId, SupplierId, ImportDate, TotalAmount) VALUES
(1, 5, 1, '2024-12-01 08:00:00', 2430000.00),
(2, 5, 3, '2025-01-05 09:00:00', 2880000.00),
(3, 6, 2, '2025-02-10 08:30:00', 3510000.00),
(4, 6, 4, '2025-03-01 09:00:00', 4290000.00)
ON DUPLICATE KEY UPDATE TotalAmount = VALUES(TotalAmount);
