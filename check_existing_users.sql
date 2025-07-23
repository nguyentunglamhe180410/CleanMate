-- Script kiểm tra user có sẵn trong database
USE CleanMateDB_Main;
GO

-- Kiểm tra tất cả user trong bảng AspNetUsers
PRINT '=== Tất cả user trong database ===';
SELECT 
    Id,
    UserName,
    FullName,
    Email,
    PhoneNumber,
    DOB,
    CASE Gender WHEN 1 THEN 'Nam' ELSE 'Nữ' END as Gender,
    CreatedDate
FROM AspNetUsers
ORDER BY Id;

-- Kiểm tra user với role Customer
PRINT '=== User có role Customer ===';
SELECT 
    u.Id,
    u.UserName,
    u.FullName,
    u.Email,
    u.PhoneNumber,
    r.Name as Role
FROM AspNetUsers u
LEFT JOIN AspNetUserRoles ur ON u.Id = ur.UserId
LEFT JOIN AspNetRoles r ON ur.RoleId = r.Id
WHERE r.Name = 'Customer'
ORDER BY u.Id;

-- Kiểm tra user cụ thể (user-001 đến user-005)
PRINT '=== Kiểm tra user-001 đến user-005 ===';
SELECT 
    u.Id,
    u.UserName,
    u.FullName,
    u.Email,
    u.PhoneNumber,
    u.DOB,
    CASE u.Gender WHEN 1 THEN 'Nam' ELSE 'Nữ' END as Gender,
    u.CreatedDate,
    r.Name as Role,
    w.Balance
FROM AspNetUsers u
LEFT JOIN AspNetUserRoles ur ON u.Id = ur.UserId
LEFT JOIN AspNetRoles r ON ur.RoleId = r.Id
LEFT JOIN User_Wallet w ON u.Id = w.UserId
WHERE u.Id IN ('user-001', 'user-002', 'user-003', 'user-004', 'user-005')
ORDER BY u.Id;

-- Kiểm tra cấu trúc bảng AspNetUsers
PRINT '=== Cấu trúc bảng AspNetUsers ===';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'AspNetUsers' 
ORDER BY ORDINAL_POSITION; 