-- Script test việc load user từ database
USE CleanMateDB_Main;
GO

-- Test query giống như trong CustomerRepository.getAllCustomerIds()
PRINT '=== Test getAllCustomerIds() query ===';
SELECT u.Id 
FROM AspNetUsers u
JOIN AspNetUserRoles ur ON u.Id = ur.UserId
JOIN AspNetRoles r ON ur.RoleId = r.Id
WHERE r.Name = 'Customer'
ORDER BY u.Id;

-- Kiểm tra có bao nhiêu user với role Customer
PRINT '=== Số lượng user với role Customer ===';
SELECT COUNT(*) as CustomerCount
FROM AspNetUsers u
JOIN AspNetUserRoles ur ON u.Id = ur.UserId
JOIN AspNetRoles r ON ur.RoleId = r.Id
WHERE r.Name = 'Customer';

-- Kiểm tra tất cả role có sẵn
PRINT '=== Tất cả role có sẵn ===';
SELECT Id, Name FROM AspNetRoles ORDER BY Name;

-- Kiểm tra user và role của họ
PRINT '=== User và role của họ ===';
SELECT 
    u.Id,
    u.UserName,
    u.FullName,
    r.Name as Role
FROM AspNetUsers u
LEFT JOIN AspNetUserRoles ur ON u.Id = ur.UserId
LEFT JOIN AspNetRoles r ON ur.RoleId = r.Id
ORDER BY u.Id; 