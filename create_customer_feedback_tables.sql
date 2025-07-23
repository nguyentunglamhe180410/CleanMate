-- Script tạo bảng Customer và Feedback cho CleanMate
USE CleanMateDB_Main;
GO

-- Tạo bảng AspNetUsers (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='AspNetUsers' AND xtype='U')
BEGIN
    CREATE TABLE AspNetUsers (
        Id NVARCHAR(450) PRIMARY KEY,
        UserName NVARCHAR(256) NOT NULL,
        NormalizedUserName NVARCHAR(256),
        Email NVARCHAR(256),
        NormalizedEmail NVARCHAR(256),
        EmailConfirmed BIT DEFAULT 0,
        PasswordHash NVARCHAR(MAX),
        SecurityStamp NVARCHAR(MAX),
        ConcurrencyStamp NVARCHAR(MAX),
        PhoneNumber NVARCHAR(50),
        PhoneNumberConfirmed BIT DEFAULT 0,
        TwoFactorEnabled BIT DEFAULT 0,
        LockoutEnd DATETIMEOFFSET,
        LockoutEnabled BIT DEFAULT 0,
        AccessFailedCount INT DEFAULT 0,
        FullName NVARCHAR(100),
        CreatedDate DATETIME2 DEFAULT GETDATE(),
        ProfileImage NVARCHAR(500)
    );
    PRINT 'Đã tạo bảng AspNetUsers';
END
ELSE
BEGIN
    PRINT 'Bảng AspNetUsers đã tồn tại';
END

-- Tạo bảng AspNetRoles (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='AspNetRoles' AND xtype='U')
BEGIN
    CREATE TABLE AspNetRoles (
        Id NVARCHAR(450) PRIMARY KEY,
        Name NVARCHAR(256) NOT NULL,
        NormalizedName NVARCHAR(256),
        ConcurrencyStamp NVARCHAR(MAX)
    );
    PRINT 'Đã tạo bảng AspNetRoles';
END
ELSE
BEGIN
    PRINT 'Bảng AspNetRoles đã tồn tại';
END

-- Tạo bảng AspNetUserRoles (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='AspNetUserRoles' AND xtype='U')
BEGIN
    CREATE TABLE AspNetUserRoles (
        UserId NVARCHAR(450) NOT NULL,
        RoleId NVARCHAR(450) NOT NULL,
        PRIMARY KEY (UserId, RoleId),
        FOREIGN KEY (UserId) REFERENCES AspNetUsers(Id),
        FOREIGN KEY (RoleId) REFERENCES AspNetRoles(Id)
    );
    PRINT 'Đã tạo bảng AspNetUserRoles';
END
ELSE
BEGIN
    PRINT 'Bảng AspNetUserRoles đã tồn tại';
END

-- Tạo bảng User_Wallet (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='User_Wallet' AND xtype='U')
BEGIN
    CREATE TABLE User_Wallet (
        WalletId INT IDENTITY(1,1) PRIMARY KEY,
        UserId NVARCHAR(450) NOT NULL,
        Balance DECIMAL(10,2) DEFAULT 0,
        CreatedAt DATETIME2 DEFAULT GETDATE(),
        FOREIGN KEY (UserId) REFERENCES AspNetUsers(Id)
    );
    PRINT 'Đã tạo bảng User_Wallet';
END
ELSE
BEGIN
    PRINT 'Bảng User_Wallet đã tồn tại';
END

-- Tạo bảng CustomerAddresses (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='CustomerAddresses' AND xtype='U')
BEGIN
    CREATE TABLE CustomerAddresses (
        AddressId INT IDENTITY(1,1) PRIMARY KEY,
        UserId NVARCHAR(450) NOT NULL,
        GG_FormattedAddress NVARCHAR(500),
        GG_DispalyName NVARCHAR(200),
        GG_PlaceId NVARCHAR(100),
        AddressNo NVARCHAR(50),
        IsInUse BIT DEFAULT 1,
        IsDefault BIT DEFAULT 0,
        Latitude DECIMAL(10,8),
        Longitude DECIMAL(11,8),
        FOREIGN KEY (UserId) REFERENCES AspNetUsers(Id)
    );
    PRINT 'Đã tạo bảng CustomerAddresses';
END
ELSE
BEGIN
    PRINT 'Bảng CustomerAddresses đã tồn tại';
END

-- Tạo bảng WalletTransactions (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='WalletTransactions' AND xtype='U')
BEGIN
    CREATE TABLE WalletTransactions (
        TransactionId INT IDENTITY(1,1) PRIMARY KEY,
        WalletId INT NOT NULL,
        Amount DECIMAL(10,2) NOT NULL,
        TransactionType NVARCHAR(50) NOT NULL,
        Description NVARCHAR(500),
        CreatedAt DATETIME2 DEFAULT GETDATE(),
        FOREIGN KEY (WalletId) REFERENCES User_Wallet(WalletId)
    );
    PRINT 'Đã tạo bảng WalletTransactions';
END
ELSE
BEGIN
    PRINT 'Bảng WalletTransactions đã tồn tại';
END

-- Tạo bảng Booking (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Booking' AND xtype='U')
BEGIN
    CREATE TABLE Booking (
        BookingId INT IDENTITY(1,1) PRIMARY KEY,
        UserId NVARCHAR(450) NOT NULL,
        ServicePriceId INT,
        CleanerId NVARCHAR(450),
        AddressId INT,
        TotalPrice DECIMAL(10,2),
        Note NVARCHAR(500),
        CreatedAt DATETIME2 DEFAULT GETDATE(),
        UpdatedAt DATETIME2 DEFAULT GETDATE(),
        BookingStatusId INT DEFAULT 1,
        FOREIGN KEY (UserId) REFERENCES AspNetUsers(Id)
    );
    PRINT 'Đã tạo bảng Booking';
END
ELSE
BEGIN
    PRINT 'Bảng Booking đã tồn tại';
END

-- Tạo bảng Feedbacks
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Feedbacks' AND xtype='U')
BEGIN
    CREATE TABLE Feedbacks (
        FeedbackId INT IDENTITY(1,1) PRIMARY KEY,
        BookingId INT NOT NULL,
        Rating DECIMAL(3,1) NOT NULL,
        Content NVARCHAR(1000),
        CreatedAt DATETIME2 DEFAULT GETDATE()
    );
    PRINT 'Đã tạo bảng Feedbacks';
END
ELSE
BEGIN
    PRINT 'Bảng Feedbacks đã tồn tại';
END

-- Insert sample data cho AspNetRoles
IF NOT EXISTS (SELECT * FROM AspNetRoles WHERE Name = 'Customer')
BEGIN
    INSERT INTO AspNetRoles (Id, Name, NormalizedName)
    VALUES 
        ('1', 'Customer', 'CUSTOMER'),
        ('2', 'Cleaner', 'CLEANER'),
        ('3', 'Admin', 'ADMIN');
    PRINT 'Đã insert sample data cho AspNetRoles';
END
ELSE
BEGIN
    PRINT 'Sample data cho AspNetRoles đã tồn tại';
END

-- Insert sample data cho AspNetUsers
IF NOT EXISTS (SELECT * FROM AspNetUsers WHERE UserName = 'customer1')
BEGIN
    INSERT INTO AspNetUsers (Id, UserName, Email, PhoneNumber, FullName, CreatedDate)
    VALUES 
        ('CUST001', 'customer1', 'customer1@example.com', '0123456789', N'Nguyễn Văn A', GETDATE()),
        ('CUST002', 'customer2', 'customer2@example.com', '0987654321', N'Trần Thị B', GETDATE());
    PRINT 'Đã insert sample data cho AspNetUsers';
END
ELSE
BEGIN
    PRINT 'Sample data cho AspNetUsers đã tồn tại';
END

-- Insert sample data cho AspNetUserRoles
IF NOT EXISTS (SELECT * FROM AspNetUserRoles WHERE UserId = 'CUST001')
BEGIN
    INSERT INTO AspNetUserRoles (UserId, RoleId)
    VALUES 
        ('CUST001', '1'),
        ('CUST002', '1');
    PRINT 'Đã insert sample data cho AspNetUserRoles';
END
ELSE
BEGIN
    PRINT 'Sample data cho AspNetUserRoles đã tồn tại';
END

-- Insert sample data cho User_Wallet
IF NOT EXISTS (SELECT * FROM User_Wallet WHERE UserId = 'CUST001')
BEGIN
    INSERT INTO User_Wallet (UserId, Balance)
    VALUES 
        ('CUST001', 100000),
        ('CUST002', 50000);
    PRINT 'Đã insert sample data cho User_Wallet';
END
ELSE
BEGIN
    PRINT 'Sample data cho User_Wallet đã tồn tại';
END

-- Insert sample data cho CustomerAddresses
IF NOT EXISTS (SELECT * FROM CustomerAddresses WHERE UserId = 'CUST001')
BEGIN
    INSERT INTO CustomerAddresses (UserId, GG_FormattedAddress, GG_DispalyName, AddressNo, IsDefault)
    VALUES 
        ('CUST001', N'123 Đường ABC, Quận 1, TP.HCM', N'Nhà riêng', '123', 1),
        ('CUST002', N'456 Đường XYZ, Quận 2, TP.HCM', N'Công ty', '456', 1);
    PRINT 'Đã insert sample data cho CustomerAddresses';
END
ELSE
BEGIN
    PRINT 'Sample data cho CustomerAddresses đã tồn tại';
END

-- Insert sample data cho Booking
IF NOT EXISTS (SELECT * FROM Booking WHERE UserId = 'CUST001')
BEGIN
    INSERT INTO Booking (UserId, ServicePriceId, TotalPrice, Note, BookingStatusId)
    VALUES 
        ('CUST001', 1, 200000, N'Dọn dẹp nhà bếp', 1),
        ('CUST002', 2, 150000, N'Giặt ủi quần áo', 1);
    PRINT 'Đã insert sample data cho Booking';
END
ELSE
BEGIN
    PRINT 'Sample data cho Booking đã tồn tại';
END

-- Insert sample data cho Feedbacks
IF NOT EXISTS (SELECT * FROM Feedbacks WHERE BookingId = 1)
BEGIN
    INSERT INTO Feedbacks (BookingId, Rating, Content)
    VALUES 
        (1, 5.0, N'Dịch vụ rất tốt, nhân viên chuyên nghiệp'),
        (2, 4.5, N'Chất lượng tốt, giao hàng đúng giờ');
    PRINT 'Đã insert sample data cho Feedbacks';
END
ELSE
BEGIN
    PRINT 'Sample data cho Feedbacks đã tồn tại';
END

-- Kiểm tra cấu trúc bảng
PRINT '=== Cấu trúc bảng AspNetUsers ===';
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'AspNetUsers' 
ORDER BY ORDINAL_POSITION;

PRINT '=== Cấu trúc bảng Feedbacks ===';
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'Feedbacks' 
ORDER BY ORDINAL_POSITION;

-- Kiểm tra data
PRINT '=== Data trong bảng AspNetUsers ===';
SELECT Id, UserName, FullName, Email, PhoneNumber FROM AspNetUsers;

PRINT '=== Data trong bảng Feedbacks ===';
SELECT * FROM Feedbacks;

PRINT '=== Test query Customer ===';
SELECT u.Id, u.FullName, u.Email, u.PhoneNumber, r.Name as Role
FROM AspNetUsers u
JOIN AspNetUserRoles ur ON u.Id = ur.UserId
JOIN AspNetRoles r ON ur.RoleId = r.Id
WHERE r.Name = 'Customer'; 