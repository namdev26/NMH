-- =============================================
-- supplier-service seed data
-- =============================================

INSERT INTO tblSupplier (Id, Name, Email, Contact) VALUES
(1, 'Công ty May Thượng Hải',     'contact@shanghai-fashion.com',  'Nguyễn Văn Bình - 0901234567'),
(2, 'Xưởng Dệt Hà Nội',          'info@hanoi-textile.vn',         'Trần Thị Lan - 0912345678'),
(3, 'Fashion Korea Import',       'sales@fashionkorea.com',        'Kim Ji Young - +82-10-1234-5678'),
(4, 'Công ty TNHH Thời Trang Việt','order@vietfashion.com.vn',     'Phạm Văn Hùng - 0987654321'),
(5, 'Dragon Costume Wholesale',   'wholesale@dragoncostume.com',   'Chen Wei - +86-139-1234-5678')
ON DUPLICATE KEY UPDATE Name = VALUES(Name);
