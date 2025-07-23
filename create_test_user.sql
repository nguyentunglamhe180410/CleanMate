-- Script tạo test user cho Customer Profile
USE CleanMateDB_Main;
GO

-- Tạo test user nếu chưa có
IF NOT EXISTS (SELECT * FROM AspNetUsers WHERE Id = 'test-user-id')
BEGIN
    INSERT INTO AspNetUsers (
        Id, 
        UserName, 
        Email, 
        PhoneNumber, 
        FullName, 
        DOB, 
        Gender, 
        CreatedDate, 
        EmailConfirmed, 
        LockoutEnabled
    )
    VALUES (
        'test-user-id',
        'testuser',
        'test@example.com',
        '0123456789',
        N'Nguyễn Văn Test',
        '01/01/1990',
        1, -- 1 = Nam, 0 = Nữ
        GETDATE(),
        1, -- Email confirmed
        0  -- Not locked
    );
    PRINT 'Đã tạo test user: test-user-id';
END
ELSE
BEGIN
    PRINT 'Test user đã tồn tại';
END

-- Gán role Customer cho test user
IF NOT EXISTS (SELECT * FROM AspNetUserRoles WHERE UserId = 'test-user-id' AND RoleId = '1')
BEGIN
    INSERT INTO AspNetUserRoles (UserId, RoleId)
    VALUES ('test-user-id', '1');
    PRINT 'Đã gán role Customer cho test user';
END
ELSE
BEGIN
    PRINT 'Test user đã có role Customer';
END

-- Tạo wallet cho test user
IF NOT EXISTS (SELECT * FROM User_Wallet WHERE UserId = 'test-user-id')
BEGIN
    INSERT INTO User_Wallet (UserId, Balance)
    VALUES ('test-user-id', 100000);
    PRINT 'Đã tạo wallet cho test user';
END
ELSE
BEGIN
    PRINT 'Test user đã có wallet';
END

-- Kiểm tra test user
PRINT '=== Kiểm tra test user ===';
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
WHERE u.Id = 'test-user-id'; 