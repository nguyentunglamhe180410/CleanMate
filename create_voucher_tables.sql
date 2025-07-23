-- Script tạo bảng Voucher và UserVoucher cho CleanMate
USE CleanMateDB_Main;
GO

-- Tạo bảng Voucher
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Voucher' AND xtype='U')
BEGIN
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
    PRINT 'Đã tạo bảng Voucher';
END
ELSE
BEGIN
    PRINT 'Bảng Voucher đã tồn tại';
END

-- Tạo bảng UserVoucher
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='UserVoucher' AND xtype='U')
BEGIN
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
END
ELSE
BEGIN
    PRINT 'Bảng UserVoucher đã tồn tại';
END

-- Insert sample data cho Voucher
IF NOT EXISTS (SELECT * FROM Voucher WHERE VoucherCode = 'WELCOME10')
BEGIN
    INSERT INTO Voucher (Description, DiscountPercentage, ExpireDate, VoucherCode, IsEventVoucher, CreatedBy, Status)
    VALUES 
        (N'Giảm 10% cho khách hàng mới', 10.00, '2024-12-31', 'WELCOME10', 1, 'admin', 'Active'),
        (N'Giảm 20% cho dịch vụ dọn dẹp', 20.00, '2024-12-31', 'CLEAN20', 0, 'admin', 'Active'),
        (N'Giảm 15% cho dịch vụ giặt ủi', 15.00, '2024-12-31', 'LAUNDRY15', 0, 'admin', 'Active'),
        (N'Giảm 25% cho combo dọn dẹp + giặt ủi', 25.00, '2024-12-31', 'COMBO25', 1, 'admin', 'Active');
    PRINT 'Đã insert sample data cho Voucher';
END
ELSE
BEGIN
    PRINT 'Sample data cho Voucher đã tồn tại';
END

-- Insert sample data cho UserVoucher
IF NOT EXISTS (SELECT * FROM UserVoucher WHERE UserId = 'CUST001' AND VoucherId = 1)
BEGIN
    INSERT INTO UserVoucher (UserId, VoucherId, Quantity, IsUsed)
    VALUES 
        ('CUST001', 1, 2, 0),
        ('CUST001', 2, 1, 0),
        ('CUST002', 1, 1, 0),
        ('CUST002', 3, 3, 0);
    PRINT 'Đã insert sample data cho UserVoucher';
END
ELSE
BEGIN
    PRINT 'Sample data cho UserVoucher đã tồn tại';
END

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