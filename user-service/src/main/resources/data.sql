-- =============================================
-- user-service seed data
-- =============================================

-- Addresses
INSERT INTO tblAddress (Id, City, Street) VALUES
(1, 'Hà Nội',   '12 Phố Huế, Hai Bà Trưng'),
(2, 'Hà Nội',   '45 Bà Triệu, Hoàn Kiếm'),
(3, 'TP.HCM',   '88 Lê Lợi, Quận 1'),
(4, 'Đà Nẵng',  '200 Nguyễn Văn Linh, Thanh Khê'),
(5, 'Hà Nội',   '30 Trần Duy Hưng, Cầu Giấy'),
(6, 'Hải Phòng','55 Lạch Tray, Ngô Quyền'),
(7, 'Cần Thơ',  '10 Trần Phú, Ninh Kiều')
ON DUPLICATE KEY UPDATE City = VALUES(City);

-- Full Names
INSERT INTO tblFullName (Id, FirstName, LastName) VALUES
(1, 'Nguyễn Văn', 'An'),
(2, 'Trần Thị',   'Bình'),
(3, 'Lê Văn',     'Cường'),
(4, 'Phạm Thị',   'Dung'),
(5, 'Hoàng Minh', 'Tuấn'),
(6, 'Vũ Thị',     'Hoa'),
(7, 'Đặng Văn',   'Khoa')
ON DUPLICATE KEY UPDATE FirstName = VALUES(FirstName);

-- Users (Discriminator: CUSTOMER, STAFF, MANAGER)
INSERT INTO tblUser (Id, Discriminator, Username, Password, AddressId, FullNameId) VALUES
(1, 'CUSTOMER', 'customer1', 'pass123', 1, 1),
(2, 'CUSTOMER', 'customer2', 'pass123', 2, 2),
(3, 'CUSTOMER', 'customer3', 'pass123', 6, 6),
(4, 'STAFF',    'staff1',    'pass123', 3, 3),
(5, 'MANAGER',  'manager1',  'pass123', 4, 4),
(6, 'MANAGER',  'manager2',  'pass123', 5, 5),
(7, 'CUSTOMER', 'customer4', 'pass123', 7, 7)
ON DUPLICATE KEY UPDATE Username = VALUES(Username);

-- Customers
INSERT INTO tblCustomer (UserId, RewardPoint, CustomerRanking) VALUES
(1,  350,  'SILVER'),
(2, 1200,  'GOLD'),
(3,  800,  'SILVER'),
(7,    0,  'BRONZE')
ON DUPLICATE KEY UPDATE RewardPoint = VALUES(RewardPoint);

-- Staff
INSERT INTO tblStaff (UserId, Salary, Position) VALUES
(4, 12000000.00, 'Sales Associate')
ON DUPLICATE KEY UPDATE Salary = VALUES(Salary);

-- Manager -> cần insert tblStaff trước, rồi tblManager
INSERT INTO tblStaff (UserId, Salary, Position) VALUES
(5, 25000000.00, 'Store Manager'),
(6, 30000000.00, 'Regional Manager')
ON DUPLICATE KEY UPDATE Salary = VALUES(Salary);

INSERT INTO tblManager (UserId, ManagerCode, Title) VALUES
(5, 'MGR001', 'Quản lý cửa hàng'),
(6, 'MGR002', 'Quản lý khu vực')
ON DUPLICATE KEY UPDATE ManagerCode = VALUES(ManagerCode);
