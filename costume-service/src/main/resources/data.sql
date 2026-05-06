-- =============================================
-- costume-service seed data
-- =============================================

INSERT INTO tblCostumeLine (Id, Name, Description) VALUES
(1, 'Cổ Trang',  'Trang phục cổ trang truyền thống Việt Nam và Trung Hoa'),
(2, 'Hiện Đại',  'Trang phục thời trang hiện đại, phù hợp nhiều dịp'),
(3, 'Lễ Hội',   'Trang phục dùng cho lễ hội, sự kiện đặc biệt'),
(4, 'Thể Thao',  'Trang phục thể thao chuyên dụng và năng động')
ON DUPLICATE KEY UPDATE Name = VALUES(Name);

INSERT INTO tblCostume (Id, Name, Category, Size, Color, Material, SellPrice, StockQuantity, CostumeLineId) VALUES
(1,  'Áo Dài Truyền Thống',  'Nữ',    'M',  'Đỏ',        'Lụa',           850000.00,  10, 1),
(2,  'Hanfu Nam Bào',        'Nam',   'L',  'Xanh Navy', 'Cotton Lụa',   1200000.00,  8,  1),
(3,  'Áo Dài Cách Tân',     'Nữ',    'S',  'Trắng',     'Voan',          750000.00,  12, 1),
(4,  'Kimono Nhật',         'Nữ',    'M',  'Hồng',      'Lụa Nhật',     1500000.00,  5,  1),
(5,  'Vest Nam Công Sở',    'Nam',   'XL', 'Đen',       'Polyester',    1500000.00,  15, 2),
(6,  'Đầm Dự Tiệc Vàng',   'Nữ',    'M',  'Vàng Gold', 'Satin',        1800000.00,  7,  2),
(7,  'Bộ Suit Nữ',          'Nữ',    'L',  'Xám',       'Wool',         1650000.00,  9,  2),
(8,  'Trang Phục Halloween', 'Unisex','L',  'Đen',       'Vải Tổng Hợp',  500000.00,  20, 3),
(9,  'Áo Dài Tết Gấm Đỏ',  'Nữ',    'M',  'Đỏ Gấm',   'Gấm',           950000.00,  11, 3),
(10, 'Trang Phục Carnival',  'Unisex','M',  'Nhiều Màu', 'Vải Tổng Hợp',  680000.00,  14, 3),
(11, 'Áo Thể Thao Nam',     'Nam',   'L',  'Xanh Lam',  'Thun Co Giãn',  350000.00,  25, 4),
(12, 'Quần Short Thể Thao', 'Unisex','M',  'Đen',       'Thun',          250000.00,  30, 4),
(13, 'Áo Polo Nữ',          'Nữ',    'S',  'Hồng',      'Cotton Pique',  400000.00,  18, 4),
(14, 'Bộ Yoga Nữ',          'Nữ',    'M',  'Tím',       'Thun Co Giãn',  550000.00,  16, 4)
ON DUPLICATE KEY UPDATE Name = VALUES(Name), StockQuantity = VALUES(StockQuantity);

INSERT INTO tblCostumeDetail (Id, CostumeId, ImportPrice, Quantity, Note, ImportingBillId) VALUES
(1,  1, 510000.00, 1, 'Lô hàng đầu năm',    1),
(2,  2, 720000.00, 1, 'Nhập từ TQ',          1),
(3,  3, 450000.00, 1, 'Hàng chất lượng cao', 1),
(4,  5, 900000.00, 1, 'Suit cao cấp',        2),
(5,  6,1080000.00, 1, 'Đầm nhập KR',         2),
(6,  9, 570000.00, 1, 'Hàng Tết',            3),
(7, 11, 210000.00, 1, 'Thể thao mùa hè',     3),
(8, 12, 150000.00, 1, 'Combo thể thao',      3)
ON DUPLICATE KEY UPDATE ImportPrice = VALUES(ImportPrice);

-- BillCostumeDetail: khớp với billId trong bill_db (1-20)
INSERT INTO tblBillCostumeDetail (Id, BillId, CostumeId, Quantity, Price) VALUES
(1,  1,  1, 2, 850000.00),
(2,  1,  5, 1,1500000.00),
(3,  2,  6, 1,1800000.00),
(4,  2,  9, 2, 950000.00),
(5,  3,  2, 1,1200000.00),
(6,  3, 11, 3, 350000.00),
(7,  4,  1, 1, 850000.00),
(8,  4,  3, 2, 750000.00),
(9,  5,  8, 4, 500000.00),
(10, 5, 12, 2, 250000.00),
(11, 6, 13, 1, 400000.00),
(12, 6,  6, 1,1800000.00),
(13, 7,  9, 3, 950000.00),
(14, 8,  5, 2,1500000.00),
(15, 9,  1, 1, 850000.00),
(16, 9, 14, 1, 550000.00),
(17,10,  7, 1,1650000.00),
(18,10,  4, 1,1500000.00),
(19,11,  2, 2,1200000.00),
(20,12, 10, 3, 680000.00),
(21,13,  1, 2, 850000.00),
(22,13,  6, 1,1800000.00),
(23,14,  5, 1,1500000.00),
(24,14, 13, 2, 400000.00),
(25,15,  9, 2, 950000.00),
(26,16,  8, 3, 500000.00),
(27,17,  3, 1, 750000.00),
(28,17,  9, 1, 950000.00),
(29,18,  6, 2,1800000.00),
(30,18, 11, 1, 350000.00),
(31,19,  2, 1,1200000.00),
(32,19, 14, 2, 550000.00),
(33,20,  5, 1,1500000.00),
(34,20, 13, 3, 400000.00)
ON DUPLICATE KEY UPDATE Quantity = VALUES(Quantity);
