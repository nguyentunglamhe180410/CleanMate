-- Script sửa bảng Voucher cho CleanMate
USE CleanMateDB_Main;
GO

-- Xóa bảng cũ nếu có
IF EXISTS (SELECT * FROM sysobjects WHERE name='UserVoucher' AND xtype='U')
BEGIN
    DROP TABLE UserVoucher;
    PRINT 'Đã xóa bảng UserVoucher cũ';
END

IF EXISTS (SELECT * FROM sysobjects WHERE name='Voucher' AND xtype='U')
BEGIN
    DROP TABLE Voucher;
    PRINT 'Đã xóa bảng Voucher cũ';
END

-- Tạo lại bảng Voucher với cấu trúc đúng
CREATE TABLE Voucher (
    VoucherId INT IDENTITY(1,1) PRIMARY KEY,
    Description NVARCHAR(500) NOT NULL,
    DiscountPercentage DECIMAL(5,2) NOT NULL,
    CreatedAt DATETIME2 DEFAULT GETDATE(),
    ExpireDate DATE NOT NULL,
    VoucherCode NVARCHAR(50) UNIQUE NOT NULL,
    IsEventVoucher BIT DEFAULT 0,
    CreatedBy NVARCHAR(100),
    Status NVARCHAR(20) DEFAULT 'Active'
);
PRINT 'Đã tạo bảng Voucher với cấu trúc đúng';

-- Tạo lại bảng UserVoucher
CREATE TABLE UserVoucher (
    UserVoucherId INT IDENTITY(1,1) PRIMARY KEY,
    UserId NVARCHAR(100) NOT NULL,
    VoucherId INT NOT NULL,
    Quantity INT DEFAULT 1,
    IsUsed BIT DEFAULT 0,
    UsedAt DATETIME2 NULL,
    FOREIGN KEY (VoucherId) REFERENCES Voucher(VoucherId)
);
PRINT 'Đã tạo bảng UserVoucher';

-- Insert sample data cho Voucher
INSERT INTO Voucher (Description, DiscountPercentage, ExpireDate, VoucherCode, IsEventVoucher, CreatedBy, Status)
VALUES 
    (N'Giảm 10% cho khách hàng mới', 10.00, '2024-12-31', 'WELCOME10', 1, 'admin', 'Active'),
    (N'Giảm 20% cho dịch vụ dọn dẹp', 20.00, '2024-12-31', 'CLEAN20', 0, 'admin', 'Active'),
    (N'Giảm 15% cho dịch vụ giặt ủi', 15.00, '2024-12-31', 'LAUNDRY15', 0, 'admin', 'Active'),
    (N'Giảm 25% cho combo dọn dẹp + giặt ủi', 25.00, '2024-12-31', 'COMBO25', 1, 'admin', 'Active');
PRINT 'Đã insert sample data cho Voucher';

-- Insert sample data cho UserVoucher
INSERT INTO UserVoucher (UserId, VoucherId, Quantity, IsUsed)
VALUES 
    ('CUST001', 1, 2, 0),
    ('CUST001', 2, 1, 0),
    ('CUST002', 1, 1, 0),
    ('CUST002', 3, 3, 0);
PRINT 'Đã insert sample data cho UserVoucher';

-- Kiểm tra cấu trúc bảng
PRINT '=== Cấu trúc bảng Voucher ===';
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'Voucher' 
ORDER BY ORDINAL_POSITION;

PRINT '=== Cấu trúc bảng UserVoucher ===';
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'UserVoucher' 
ORDER BY ORDINAL_POSITION;

-- Kiểm tra data
PRINT '=== Data trong bảng Voucher ===';
SELECT * FROM Voucher;

PRINT '=== Data trong bảng UserVoucher ===';
SELECT * FROM UserVoucher;

PRINT '=== Test query với DiscountPercentage ===';
SELECT VoucherId, Description, DiscountPercentage, VoucherCode 
FROM Voucher 
WHERE Status = 'Active'; 